package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.ProductRequestDTO;
import com.minhhai.ecommercebe.dto.request.ProductSkuUpdateRequestDTO;
import com.minhhai.ecommercebe.dto.request.ProductUpdateRequestDTO;
import com.minhhai.ecommercebe.dto.response.ProductDetailResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.ProductMapper;
import com.minhhai.ecommercebe.model.*;
import com.minhhai.ecommercebe.repository.CategoryRepository;
import com.minhhai.ecommercebe.repository.ProductRepository;
import com.minhhai.ecommercebe.repository.ShopRepository;
import com.minhhai.ecommercebe.util.commons.SecurityUtil;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    public ProductDetailResponseDTO createNewProduct(ProductRequestDTO productRequestDTO, MultipartFile fileThumbnail) {

        log.info("------------ Create new product request --------------");
        Product newProduct = productMapper.toEntity(productRequestDTO);

        // Set category
        Category category = categoryRepository.findByName(productRequestDTO.getCategoryName()).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        newProduct.setCategory(category);

        // Set product for productSku
        newProduct.getProductSku().forEach(productSku -> {
            productSku.setProduct(newProduct);
        });

        // upload thumbnail to cloudinary and set url for entity
        if (!fileThumbnail.isEmpty()) {
            try {
                String urlThumbnail = cloudinaryService.uploadFile(fileThumbnail, "ecommerce")
                        .get("secure_url").toString();
                newProduct.setThumbnail(urlThumbnail);
            } catch (IOException e) {
                throw new AppException(ErrorCode.UPLOAD_CLOUDINARY_FAILED);
            }
        } else {
            // Xét 1 thumbnail mặc định
            newProduct.setThumbnail("string");
        }


        User userCreateProduct = SecurityUtil.getCurrentUser();
        Shop shop = shopRepository.findShopByUserId(userCreateProduct.getId()).orElseThrow(
                () -> new AppException(ErrorCode.SHOP_NOT_EXISTED));
        newProduct.setShop(shop);

        productRepository.save(newProduct);

        log.info("--------- Create new product successfully id = {} ------------", newProduct.getId());
        return productMapper.toDetailResponseDTO(newProduct);
    }

    public ProductDetailResponseDTO updateProduct(Long productId,
                                                  ProductUpdateRequestDTO productUpdateRequestDTO,
                                                  MultipartFile fileThumbnail) {
        log.info("----------- Update product request -------------");

        Product productUpdate = productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        productUpdate.setName(productUpdateRequestDTO.getName());
        productUpdate.setShortDesc(productUpdateRequestDTO.getShortDesc());
        productUpdate.setLongDesc(productUpdateRequestDTO.getLongDesc());

        if (!fileThumbnail.isEmpty()) {
            try {
                // Xóa thumbnail cũ qua publicId nhưng nên cần phải xem có phải thumbnail mặc định không nếu không
                // sẽ xóa nhầm trong trường hợp thumbnail mặc định cũng lưu trên cloudinary, hoặc có thể không cần xóa.
                String publicId = cloudinaryService.extractPublicIdFromUrl(productUpdate.getThumbnail());
                if (!publicId.isEmpty()) {
                    cloudinaryService.deleteFile(publicId);
                }

                String urlThumbnail = cloudinaryService.uploadFile(fileThumbnail, "ecommerce")
                        .get("secure_url").toString();
                productUpdate.setThumbnail(urlThumbnail);
            } catch (IOException e) {
                throw new AppException(ErrorCode.UPLOAD_CLOUDINARY_FAILED);
            }
        }

        Category category = categoryRepository.findByName(productUpdateRequestDTO.getCategoryName())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        productUpdate.setCategory(category);

        // update, add new product sku
        Map<Long, ProductSku> currentSkuMap = productUpdate.getProductSku().stream()
                .collect(Collectors.toMap(ProductSku::getId, sku -> sku));

        for (ProductSkuUpdateRequestDTO incomingSku : productUpdateRequestDTO.getProductSku()) {
            if (incomingSku.getId() != null) {
                // Tìm SKU cũ để update
                ProductSku existingSku = currentSkuMap.get(incomingSku.getId());

                if (existingSku != null) {
                    existingSku.setSize(incomingSku.getSize());
                    existingSku.setColor(incomingSku.getColor());
                    existingSku.setPriceEach(incomingSku.getPriceEach());
                    existingSku.setQuantity(incomingSku.getQuantity());
                } else {
                    throw new AppException(ErrorCode.PRODUCT_SKU_NOT_EXISTED);
                }
            } else {
                // Thêm SKU mới
                ProductSku newSku = ProductSku.builder()
                        .size(incomingSku.getSize())
                        .color(incomingSku.getColor())
                        .priceEach(incomingSku.getPriceEach())
                        .quantity(incomingSku.getQuantity())
                        .product(productUpdate)
                        .build();

                productUpdate.getProductSku().add(newSku);

            }
        }

        productRepository.save(productUpdate);

        log.info("----------- Updated product successfully id = {} ------------", productUpdate.getId());
        return productMapper.toDetailResponseDTO(productUpdate);
    }

    public ProductDetailResponseDTO getProductDetailByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toDetailResponseDTO(product);
    }

//    public PageResponse<List<ProductResponseDTO>> getAllProducts(Pageable pageable, String[] filters) {
//        return null;
//    }
}

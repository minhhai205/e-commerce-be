package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.ProductRequestDTO;
import com.minhhai.ecommercebe.dto.response.ProductDetailResponseDTO;
import com.minhhai.ecommercebe.dto.response.ProductResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.ProductMapper;
import com.minhhai.ecommercebe.model.Category;
import com.minhhai.ecommercebe.model.Product;
import com.minhhai.ecommercebe.model.Shop;
import com.minhhai.ecommercebe.model.User;
import com.minhhai.ecommercebe.repository.CategoryRepository;
import com.minhhai.ecommercebe.repository.ProductRepository;
import com.minhhai.ecommercebe.repository.ShopRepository;
import com.minhhai.ecommercebe.util.commons.SecurityUtil;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    public ProductDetailResponseDTO createNewProduct(ProductRequestDTO productRequestDTO) {

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

        User userCreateProduct = SecurityUtil.getCurrentUser();
        Shop shop = shopRepository.findShopByUserId(userCreateProduct.getId()).orElseThrow(
                () -> new AppException(ErrorCode.SHOP_NOT_EXISTED));
        newProduct.setShop(shop);

        productRepository.save(newProduct);

        log.info("--------- Create new product successfully id = {} ------------", newProduct.getId());
        return productMapper.toDetailResponseDTO(newProduct);
    }

//    public ProductDetailResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
//        return null;
//    }
}

package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.CartRequestDTO;
import com.minhhai.ecommercebe.dto.response.CartResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.CartMapper;
import com.minhhai.ecommercebe.model.Cart;
import com.minhhai.ecommercebe.model.CartDetail;
import com.minhhai.ecommercebe.model.ProductSku;
import com.minhhai.ecommercebe.repository.CartRepository;
import com.minhhai.ecommercebe.repository.ProductSkuRepository;
import com.minhhai.ecommercebe.util.commons.SecurityUtil;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final ProductSkuRepository productSkuRepository;
    private final CartMapper cartMapper;

    public CartResponseDTO addToCart(CartRequestDTO cartRequestDTO) {
        log.info("-------------- Add to Cart --------------");
        Long currentUserId = SecurityUtil.getCurrentUser().getId();

        Cart cart = cartRepository.findCartByUserId(currentUserId).orElseThrow(
                () -> new AppException(ErrorCode.CART_NOT_EXISTED));

        CartDetail existingItem = cart.getCartDetails().stream()
                .filter(item -> item.getProductSku().getId().equals(cartRequestDTO.getProductSkuId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartRequestDTO.getQuantity());
        } else {
            ProductSku productSku = productSkuRepository.findById(cartRequestDTO.getProductSkuId()).orElseThrow(
                    () -> new AppException(ErrorCode.PRODUCT_SKU_NOT_EXISTED));

            CartDetail newCartItem = CartDetail.builder()
                    .productSku(productSku)
                    .quantity(cartRequestDTO.getQuantity())
                    .cart(cart)
                    .build();

            cart.getCartDetails().add(newCartItem);
        }

        cartRepository.save(cart);

        log.info("-------------- Added to Cart --------------");

        return cartMapper.toResponseDTO(cart);
    }

    public CartResponseDTO updateCart(CartRequestDTO cartRequestDTO) {
        Long currentUserId = SecurityUtil.getCurrentUser().getId();

        Cart cart = cartRepository.findCartByUserId(currentUserId).orElseThrow(
                () -> new AppException(ErrorCode.CART_NOT_EXISTED));

        CartDetail existingItem = cart.getCartDetails().stream()
                .filter(item -> item.getProductSku().getId().equals(cartRequestDTO.getProductSkuId()))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_EXISTED));

        existingItem.setQuantity(cartRequestDTO.getQuantity());

        cartRepository.save(cart);
        return cartMapper.toResponseDTO(cart);
    }

    public CartResponseDTO deleteCartItem(CartRequestDTO cartRequestDTO) {
        Long currentUserId = SecurityUtil.getCurrentUser().getId();

        Cart cart = cartRepository.findCartByUserId(currentUserId).orElseThrow(
                () -> new AppException(ErrorCode.CART_NOT_EXISTED));

        cart.getCartDetails().removeIf(
                item -> item.getProductSku().getId().equals(cartRequestDTO.getProductSkuId()));
        cartRepository.save(cart);

        return cartMapper.toResponseDTO(cart);
    }

    public CartResponseDTO getCart() {
        Long currentUserId = SecurityUtil.getCurrentUser().getId();

        Cart cart = cartRepository.findCartByUserId(currentUserId).orElseThrow(
                () -> new AppException(ErrorCode.CART_NOT_EXISTED));

        return cartMapper.toResponseDTO(cart);
    }
}

package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.OrderRequestDTO;
import com.minhhai.ecommercebe.dto.response.OrderResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.OrderMapper;
import com.minhhai.ecommercebe.model.*;
import com.minhhai.ecommercebe.repository.CartRepository;
import com.minhhai.ecommercebe.repository.OrderRepository;
import com.minhhai.ecommercebe.repository.ProductSkuRepository;
import com.minhhai.ecommercebe.util.commons.SecurityUtil;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import com.minhhai.ecommercebe.util.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductSkuRepository productSkuRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public List<OrderResponseDTO> createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("-------------- Create order -------------");

        User userOrder = SecurityUtil.getCurrentUser();
        Cart cart = cartRepository.findCartAndShopWithProductSkuByUserId(userOrder.getId()).orElseThrow(
                () -> new AppException(ErrorCode.CART_NOT_EXISTED));

        Map<Long, CartDetail> cartDetailMap = cart.getCartDetails().stream()
                .collect(Collectors.toMap(CartDetail::getId, cd -> cd));

        // map shopId và order ứng với từng shop
        Map<Integer, Order> shopWithOrdersMap = new HashMap<>();

        orderRequestDTO.getCartDetailIds().forEach(cartDetailId -> {
            CartDetail cartDetail = cartDetailMap.get(cartDetailId);
            if (cartDetail == null) {
                throw new AppException(ErrorCode.CART_ITEM_NOT_EXISTED);
            }

            // Xóa sản phẩm order ra khỏi giỏ hàng
            cart.getCartDetails().removeIf(cd -> cd.getId().equals(cartDetailId));

            ProductSku productSkuWithOrder = cartDetail.getProductSku();
            long quantityOrder = cartDetail.getQuantity();

            // Check số lượng sản phẩm còn lại
            if (productSkuWithOrder.getQuantity() < quantityOrder) {
                throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);
            }
            Shop shopWithOrders = productSkuWithOrder.getProduct().getShop();

            if (shopWithOrdersMap.get(shopWithOrders.getId()) == null) {
                Order newOrder = Order.builder()
                        .receiverAddress(orderRequestDTO.getReceiverAddress())
                        .receiverPhoneNumber(orderRequestDTO.getReceiverPhoneNumber())
                        .totalPrice(BigDecimal.ZERO)
                        .status(OrderStatus.PENDING)
                        .orderDetails(new HashSet<>())
                        .payment(new Payment())
                        .user(userOrder)
                        .shop(shopWithOrders)
                        .build();

                OrderDetail newOrderDetail = OrderDetail.builder()
                        .quantity(quantityOrder)
                        .priceEach(productSkuWithOrder.getPriceEach()
                                .multiply(BigDecimal.valueOf(quantityOrder)))
                        .productSku(productSkuWithOrder)
                        .order(newOrder)
                        .build();

                newOrder.getOrderDetails().add(newOrderDetail);
                newOrder.setTotalPrice(newOrderDetail.getPriceEach());

                shopWithOrdersMap.put(shopWithOrders.getId(), newOrder);

            } else {
                Order order = shopWithOrdersMap.get(shopWithOrders.getId());

                OrderDetail newOrderDetail = OrderDetail.builder()
                        .quantity(quantityOrder)
                        .priceEach(productSkuWithOrder.getPriceEach()
                                .multiply(BigDecimal.valueOf(quantityOrder)))
                        .productSku(productSkuWithOrder)
                        .order(order)
                        .build();

                order.getOrderDetails().add(newOrderDetail);
                order.setTotalPrice(order.getTotalPrice().add(newOrderDetail.getPriceEach()));
            }

            // Cập nhập lại số lượng sản phẩm trong giỏ hàng
            productSkuWithOrder.setQuantity((int) (productSkuWithOrder.getQuantity() - quantityOrder));
        });


//        orderRepository.saveAll(shopWithOrdersMap.values());
        cartRepository.save(cart);
        productSkuRepository.saveAll(cart.getCartDetails().stream().map(CartDetail::getProductSku).toList());

        log.info("---------------- Order created successfully ----------------");

        return shopWithOrdersMap.values().stream().map(orderMapper::toResponseDTO).toList();
    }
}

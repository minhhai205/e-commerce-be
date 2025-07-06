package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT o FROM Order o
            JOIN FETCH o.orderDetails od
            JOIN FETCH o.payment p
            JOIN FETCH od.productSku ps
            WHERE o.user.id=:userId
            """)
    List<Order> findAllOrderByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT o FROM Order o
            JOIN FETCH o.orderDetails od
            JOIN FETCH o.payment p
            JOIN FETCH od.productSku ps
            WHERE o.shop.id=:shopId
            """)
    List<Order> findAllOrderByShopId(@Param("shopId") Integer shopId);
}

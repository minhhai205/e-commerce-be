package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @EntityGraph(attributePaths = {
            "cartDetails",
            "cartDetails.productSku"
    })
    @Query("SELECT c FROM Cart c WHERE c.user.id=:userId")
    Optional<Cart> findCartByUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {
//            "cartDetails",
//            "cartDetails.productSku",
//            "cartDetails.productSku.product.shop"
    })
    @Query("""
            SELECT c FROM Cart c
            JOIN FETCH c.cartDetails cd
            JOIN FETCH cd.productSku ps
            JOIN FETCH ps.product p
            JOIN FETCH p.shop
            WHERE c.user.id = :userId
            """)
    Optional<Cart> findCartAndShopWithProductSkuByUserId(@Param("userId") Long userId);
}

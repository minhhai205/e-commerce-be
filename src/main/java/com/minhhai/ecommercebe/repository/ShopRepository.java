package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Shop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @EntityGraph(attributePaths = {
            "products"
    })
    @Query("SELECT s FROM Shop s WHERE s.user.id=:userId")
    Optional<Shop> findShopByUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {
            "products",
    })
    Optional<Shop> findShopById(@Param("shopId") Integer shopId);
}

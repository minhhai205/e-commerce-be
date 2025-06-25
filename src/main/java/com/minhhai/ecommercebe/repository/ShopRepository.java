package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Query("SELECT s FROM Shop s WHERE s.user.id=:userId")
    Optional<Shop> findShopByUserId(Long userId);
}

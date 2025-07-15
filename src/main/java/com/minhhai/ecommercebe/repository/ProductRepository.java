package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {
            "productSku",
            "category"
    })
    Optional<Product> findById(@Param("productId") Long productId);
}

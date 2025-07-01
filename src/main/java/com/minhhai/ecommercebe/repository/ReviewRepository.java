package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    @EntityGraph(attributePaths = {
            "user"
    })
    @Query("select r FROM Review r WHERE r.product.id=:productId")
    List<Review> findReviewByProductId(@Param("productId") Long productId);
}

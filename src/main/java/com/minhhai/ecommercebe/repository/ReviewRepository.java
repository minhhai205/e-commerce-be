package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Review;
import com.minhhai.ecommercebe.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    Optional<Review> findById(Long id);
}

package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.deleted=false ")
    List<Category> findAll();

    Optional<Category> findByName(String name);
}

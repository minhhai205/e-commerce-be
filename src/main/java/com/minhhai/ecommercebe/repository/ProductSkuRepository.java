package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

}

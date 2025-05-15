package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByName(String name);

    public boolean existsByName(String name);

    Set<Permission> findByNameIn(List<String> names);
}

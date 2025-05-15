package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Role;
import com.minhhai.ecommercebe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);

    public boolean existsByName(String name);
}

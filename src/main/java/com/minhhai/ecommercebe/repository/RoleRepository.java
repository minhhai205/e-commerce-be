package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.Role;
import com.minhhai.ecommercebe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE r.name=:name")
    Optional<Role> findByName(@Param("name") String name);

    public boolean existsByName(String name);

    @Query("SELECT r FROM Role r JOIN FETCH r.permissions")
    List<Role> findAllWithPermissions();

    Set<Role> findByNameIn(List<String> names);
}

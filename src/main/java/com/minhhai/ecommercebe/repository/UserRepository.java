package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @EntityGraph(attributePaths = {
            "roles",
            "roles.permissions",
            "addresses",
    })
    @Query("SELECT u FROM User u WHERE u.deleted=false AND u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);

    @EntityGraph(attributePaths = {
            "roles",
            "roles.permissions",
            "addresses",
    })
    @Query("SELECT u FROM User u WHERE u.deleted=false AND u.username=:username")
    Optional<User> findByUsername(@Param("username") String username);

    @EntityGraph(attributePaths = {
            "roles",
            "roles.permissions",
            "addresses"
    })
    @Query("SELECT u FROM User u WHERE u.deleted=false AND u.id=:id")
    Optional<User> findById(@Param("id") Long userId);

    @EntityGraph(attributePaths = {
            "cart",
            "shop"
    })
    Page<User> findAll(@Nullable Specification<User> spec, Pageable pageable);

    @EntityGraph(attributePaths = {
            "cart",
            "shop"
    })
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {
            "roles",
            "addresses",
    })
    List<User> findByIdIn(List<Long> ids);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

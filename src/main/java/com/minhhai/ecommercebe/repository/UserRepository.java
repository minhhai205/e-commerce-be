package com.minhhai.ecommercebe.repository;

import com.minhhai.ecommercebe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.deleted=false AND u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.deleted=false AND u.username=:username")
    Optional<User> findByUsername(@Param("username") String username);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);
}

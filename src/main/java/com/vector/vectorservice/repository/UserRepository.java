package com.vector.vectorservice.repository;


import com.vector.vectorservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);

    Optional<User> findById(Long id);
}

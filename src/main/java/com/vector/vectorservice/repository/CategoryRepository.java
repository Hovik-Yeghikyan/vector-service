package com.vector.vectorservice.repository;

import com.vector.vectorservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    Optional<Category> findById(Long id);

    boolean existsByName(String name);


}

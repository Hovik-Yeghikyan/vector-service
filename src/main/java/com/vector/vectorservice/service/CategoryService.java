package com.vector.vectorservice.service;

import com.vector.vectorservice.dto.CategoryRequestDto;
import com.vector.vectorservice.dto.CategoryResponseDto;
import com.vector.vectorservice.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface CategoryService {


    Page<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto save(CategoryRequestDto categoryRequestDto, MultipartFile multipartFile) throws IOException;

    void update(CategoryResponseDto categoryResponseDto, MultipartFile multipartFile) throws IOException;

    Category findById(Long id);

    void deleteById(Long id);
}

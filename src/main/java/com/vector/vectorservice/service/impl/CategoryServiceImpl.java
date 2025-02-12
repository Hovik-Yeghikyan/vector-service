package com.vector.vectorservice.service.impl;

import com.vector.vectorservice.dto.CategoryRequestDto;
import com.vector.vectorservice.dto.CategoryResponseDto;
import com.vector.vectorservice.entity.Category;
import com.vector.vectorservice.exception.ResourceAlreadyExistsException;
import com.vector.vectorservice.exception.ResourceNotFoundException;
import com.vector.vectorservice.mapper.CategoryMapper;
import com.vector.vectorservice.repository.CategoryRepository;
import com.vector.vectorservice.service.CategoryService;
import com.vector.vectorservice.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileUtil fileUtil;


    @Override
    public Page<CategoryResponseDto> findAll(Pageable pageable) {
        Page<Category> all = categoryRepository.findAll(pageable);
        return toDtoPage(all);
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto, MultipartFile multipartFile) throws IOException {
        if (categoryRepository.findByName(categoryRequestDto.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Category with this name already exists!");
        }
        if (!multipartFile.isEmpty()) {
            String imageName = fileUtil.saveImage(multipartFile);
            categoryRequestDto.setImageName(imageName);
        }else {
            throw new ResourceNotFoundException("There are no any picture");
        }
        Category category = categoryMapper.toEntity(categoryRequestDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public Optional<CategoryResponseDto> existByName(String name) {
        Optional<Category> categoryByName = categoryRepository.findByName(name);
        if (categoryByName.isPresent()) {
            throw new ResourceAlreadyExistsException("Category with this name already exists!");
        }
        return categoryByName.map(categoryMapper::toDto);
    }

    @Override
    public void update(CategoryResponseDto categoryResponseDto, MultipartFile multipartFile) throws IOException {
        Category response = findById(categoryResponseDto.getId());
        String imageName = fileUtil.saveImage(multipartFile);
        response.setImageName(imageName);
        response.setName(categoryResponseDto.getName());
        categoryRepository.save(response);
    }


    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find category with id " + id));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.delete(findById(id));

    }

    private Page<CategoryResponseDto> toDtoPage(Page<Category> categories) {
        List<CategoryResponseDto> dtos = categories.stream()
                .map(categoryMapper::toDto)
                .toList();

        return new PageImpl<>(dtos, categories.getPageable(), categories.getTotalElements());
    }
}
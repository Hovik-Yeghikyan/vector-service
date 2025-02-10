package com.vector.vectorservice.mapper;

import com.vector.vectorservice.dto.CategoryRequestDto;
import com.vector.vectorservice.dto.CategoryResponseDto;
import com.vector.vectorservice.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDto toDto(Category category);

    List<CategoryResponseDto> toDtoList(List<Category> categories);


    Category toEntity(CategoryRequestDto categoryRequestDto);

    Category toEntityFromResponse(CategoryResponseDto categoryResponseDto);

    CategoryRequestDto toRequestDto(CategoryResponseDto categoryResponseDto);


}

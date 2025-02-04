package com.vector.vectorservice.mapper;

import com.vector.vectorservice.dto.UserRequestDto;
import com.vector.vectorservice.dto.UserResponseDto;
import com.vector.vectorservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto userRequestDto);

    UserRequestDto toUserRequestDto(UserResponseDto userResponseDto);

    UserResponseDto toUserResponseDto(UserRequestDto userRequestDto);
}

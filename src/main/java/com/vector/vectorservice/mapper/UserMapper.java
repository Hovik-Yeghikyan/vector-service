package com.vector.vectorservice.mapper;

import com.vector.vectorservice.dto.UserRequestDto;
import com.vector.vectorservice.dto.UserResponseDto;
import com.vector.vectorservice.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto userRequestDto);

    UserRequestDto toUserRequestDto(UserResponseDto userResponseDto);

    UserResponseDto toUserResponseDto(UserRequestDto userRequestDto);

    @Mapping(target = "password", expression = "java(mapPassword(userRequestDto, passwordEncoder))")
    UserRequestDto regPassword(UserRequestDto userRequestDto, @Context PasswordEncoder passwordEncoder);

    default String mapPassword(UserRequestDto userRequestDto, @Context PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(userRequestDto.getPassword());
    }
}

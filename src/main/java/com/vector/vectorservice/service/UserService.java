package com.vector.vectorservice.service;


import com.vector.vectorservice.dto.UserRequestDto;
import com.vector.vectorservice.dto.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {


    UserResponseDto save(UserRequestDto userRequestDto);


    Optional<UserResponseDto> findByEmail(String email);

    UserResponseDto register(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException;

    UserResponseDto findByToken(String token);

    UserResponseDto edit(int id, UserRequestDto userRequestDto);


}

package com.vector.vectorservice.service.impl;


import com.vector.vectorservice.dto.UserRequestDto;
import com.vector.vectorservice.dto.UserResponseDto;
import com.vector.vectorservice.entity.User;
import com.vector.vectorservice.entity.enums.UserType;
import com.vector.vectorservice.exception.ResourceNotFoundException;
import com.vector.vectorservice.exception.UserAlreadyExistsException;
import com.vector.vectorservice.mapper.UserMapper;
import com.vector.vectorservice.repository.UserRepository;
import com.vector.vectorservice.service.UserService;
import com.vector.vectorservice.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${vector-service.picture.upload.directory}")
    private String uploadPath;

    private final UserRepository userRepository;
    private final SendMailServiceImpl sendMailService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final FileUtil fileUtil;


    @Override
    public UserResponseDto save(UserRequestDto userRequestDto) {
        User save = userRepository.save(userMapper.toEntity(userRequestDto));
        return userMapper.toDto(save);
    }


    @Override
    public Optional<UserResponseDto> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }


    @Override
    public UserResponseDto register(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException {
        if (userRequestDto.getEmail().isEmpty() || userRequestDto.getPassword().isEmpty() ||
                userRequestDto.getName().isEmpty() || userRequestDto.getSurname().isEmpty()) {
            return null;
        }
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        if (!multipartFile.isEmpty()) {
            String imageName = fileUtil.saveImage(multipartFile);
            userRequestDto.setPicName(imageName);
        }
        String activationToken = UUID.randomUUID().toString();
        userRequestDto.setActive(false);
        userRequestDto.setToken(activationToken);
        userRequestDto.setUserType(UserType.USER);
        UserRequestDto encodedUser = userMapper.regPassword(userRequestDto, passwordEncoder);
        UserResponseDto save = save(encodedUser);
        sendMailService.sendWelcomeMail(save);
        return save;
    }

    @Override
    public UserResponseDto findByToken(String token) {
        Optional<User> byToken = userRepository.findByToken(token);
        return byToken.map(userMapper::toDto).orElse(null);


    }

    @Override
    public void tokenActivation(UserResponseDto userResponseDto) {
        Long id = userResponseDto.getId();
        User userById = getUserById(id);
        userById.setActive(true);
        userById.setToken(null);
        userRepository.save(userById);
    }


    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Cannot find user with id " + id;
                    throw new ResourceNotFoundException(message);
                });
    }

}

package com.vector.vectorservice.controller;


import com.vector.vectorservice.dto.UserRequestDto;
import com.vector.vectorservice.dto.UserResponseDto;
import com.vector.vectorservice.mapper.UserMapper;
import com.vector.vectorservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/register")
    public String registerPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        return "register";
    }

    @PostMapping("/register")
    public String userRegister(@ModelAttribute UserRequestDto userRequestDto, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<UserResponseDto> byEmail = userService.findByEmail(userRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            UserResponseDto register = userService.register(userRequestDto, multipartFile);
            if (register == null) {
                log.info("One of the fields is empty");
                return "redirect:/register?msg=Incorrect information,please try again";
            }
            log.info("User with {} email registered successfully", userRequestDto.getEmail());
            return "redirect:/loginPage?msg=User Registered,Please verify your account!";
        } else {
            log.info("User with {} email already registered", userRequestDto.getEmail());
            return "redirect:/register?msg=Email already in use";
        }
    }
    @GetMapping("/user/verify")
    public String verifyUser(@RequestParam("token") String token) {
        UserResponseDto byToken = userService.findByToken(token);
        if (byToken == null) {
            return "redirect:/";
        }
        if (byToken.isActive()) {
            log.error("User already active! {}", byToken.getEmail());
            return "redirect:/";
        }
        userService.edit(byToken.getId(),userMapper.toUserRequestDto(byToken));

        return "redirect:/";
    }

}

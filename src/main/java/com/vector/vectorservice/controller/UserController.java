package com.vector.vectorservice.controller;


import com.vector.vectorservice.entity.User;
import com.vector.vectorservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @GetMapping("/register")
    public String registerPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        return "register";
    }

    @PostMapping("/register")
    public String userRegister(@ModelAttribute User user) {
       Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            User register = userService.register(user);
            if (register == null) {
                log.info("One of the fields is empty");
                return "redirect:/register?msg=Incorrect information,please try again";
            }
            log.info("User with {} email registered successfully", user.getEmail());
            return "redirect:/loginPage?msg=User Registered,Please verify your account!";
        } else {
            log.info("User with {} email already registered", user.getEmail());
            return "redirect:/register?msg=Email already in use";
        }
    }

    @GetMapping("/user/verify")
    public String verifyUser(@RequestParam("token") String token) {
        User byToken = userService.findByToken(token);
        if (byToken == null) {
            return "redirect:/";
        }
        if (byToken.isActive()) {
            log.error("user already active! {}", byToken.getEmail());
        }
        byToken.setActive(true);
        byToken.setToken(null);
        userService.save(byToken);

        return "redirect:/";
    }
}

package com.vector.vectorservice.service.impl;



import com.vector.vectorservice.entity.User;
import com.vector.vectorservice.entity.UserType;
import com.vector.vectorservice.repository.UserRepository;
import com.vector.vectorservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User register(User user) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()||
                user.getName().isEmpty()||user.getSurname().isEmpty()) {
            return null;
        }
        String activationToken = UUID.randomUUID().toString();
        user.setActive(false);
        user.setToken(activationToken);
        user.setUserType(UserType.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        sendMailService.sendWelcomeMail(user);
        return save;
    }

    @Override
    public User findByToken(String token) {
        return userRepository.findByToken(token).orElse(null);
    }

}

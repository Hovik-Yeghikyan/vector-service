package com.vector.vectorservice.service;


import com.vector.vectorservice.entity.User;

import java.util.Optional;

public interface UserService {


    User save(User user);


    Optional<User> findByEmail(String email);

    User register(User user);

    User findByToken(String token);
}

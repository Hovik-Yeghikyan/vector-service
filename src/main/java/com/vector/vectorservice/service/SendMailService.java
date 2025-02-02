package com.vector.vectorservice.service;

import com.vector.vectorservice.entity.User;

public interface SendMailService {


    void sendWelcomeMail(User user);
}

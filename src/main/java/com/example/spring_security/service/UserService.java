package com.example.spring_security.service;

import com.example.spring_security.model.dto.request.UserRequest;

public interface UserService {
    void addUser(UserRequest userRequest);

    void confirmUser(String token);
}

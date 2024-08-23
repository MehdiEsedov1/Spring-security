package com.example.spring_security.mapper.Impl;

import com.example.spring_security.model.UserEntity;
import com.example.spring_security.model.dto.request.UserRequest;

public interface UserMapperImpl {
    public UserEntity mapper(UserRequest userRequest);
}

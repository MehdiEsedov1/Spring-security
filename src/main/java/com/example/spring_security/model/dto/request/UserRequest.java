package com.example.spring_security.model.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRequest {
    public String username;
    public String email;
    public String password;
}

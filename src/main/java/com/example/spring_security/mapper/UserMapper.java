package com.example.spring_security.mapper;

import com.example.spring_security.mapper.Impl.UserMapperImpl;
import com.example.spring_security.model.Role;
import com.example.spring_security.model.UserEntity;
import com.example.spring_security.model.dto.request.UserRequest;
import com.example.spring_security.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserMapper implements UserMapperImpl {
    public final RoleRepo roleRepo;

    @Override
    public UserEntity mapper(UserRequest userRequest) {
        Role role = roleRepo.findByName("USER").get();

        return UserEntity.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .roles(Collections.singletonList(role))
                .password(userRequest.getPassword())
                .isEnable(false)
                .build();
    }
}

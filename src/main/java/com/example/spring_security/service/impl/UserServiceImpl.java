package com.example.spring_security.service.impl;

import com.example.spring_security.mail.MailServiceImpl;
import com.example.spring_security.mapper.UserMapper;
import com.example.spring_security.model.UserEntity;
import com.example.spring_security.model.dto.request.UserRequest;
import com.example.spring_security.repository.TokenRepo;
import com.example.spring_security.repository.UserRepo;
import com.example.spring_security.service.UserService;
import com.example.spring_security.token.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final MailServiceImpl mailService;
    private final TokenRepo tokenRepo;

    @Override
    public void addUser(UserRequest userRequest) {
        String token = UUID.randomUUID().toString();
        String confirmationLink = "http://localhost:8090/confirm?token=" + token;

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setEmail(userRequest.getEmail());
        confirmationToken.setToken(token);

        tokenRepo.save(confirmationToken);

        mailService.sendSimpleEmail(userRequest.getEmail(), "Confirm your registration",
                "Please confirm your registration by clicking the following link: " + confirmationLink);

        userRepo.save(userMapper.mapper(userRequest));
    }

    public void confirmUser(String token) {
        ConfirmationToken confirmationToken = tokenRepo.findConfirmationTokenByToken(token);
        if (confirmationToken != null) {
            UserEntity user = userRepo.findByEmail(confirmationToken.getEmail()).orElseThrow();
            user.setIsEnable(true);
            userRepo.save(user);
        }
    }
}

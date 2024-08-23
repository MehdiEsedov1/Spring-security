package com.example.spring_security.controller;

import com.example.spring_security.model.dto.response.AuthResponseDTO;
import com.example.spring_security.repository.RoleRepo;
import com.example.spring_security.repository.UserRepo;
import com.example.spring_security.security.JWTGenerator;
import com.example.spring_security.service.UserService;
import lombok.RequiredArgsConstructor;
import com.example.spring_security.model.dto.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {
    public final AuthenticationManager authenticationManager;
    public final UserRepo userRepo;
    public final UserService userService;
    public final RoleRepo roleRepo;
    public final PasswordEncoder passwordEncoder;
    public final JWTGenerator jwtGenerator;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserRequest userRequest) {
        if (userRepo.existsByUsername(userRequest.username)) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userService.addUser(userRequest);

        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserRequest userRequest) {
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public String confirmUser(@RequestParam("token") String token) {
        userService.confirmUser(token);
        return "User confirmed!";
    }

    @GetMapping("/home")
    public String sayHello() {
        return "Hello, World !!!";
    }
}

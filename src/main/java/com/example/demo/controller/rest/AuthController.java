package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.reponses.MessageResponse;
import com.example.demo.reponses.TokenResponse;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.EmailPasswordRequest;
import com.example.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/auth")
@Api(tags = "Auth", description = "Endpoint")
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Connection with your user account", description = "You get a token and user role when you signIn correctly")
    @PostMapping(path = "/signIn")
    public ResponseEntity<Object> authenticationUser(
            @RequestBody EmailPasswordRequest content) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    content.getEmail(), content.getPassword()));
            TokenResponse tokenResponse = userService.getTokenResponse(content);

            return ResponseEntity.status(200).body(tokenResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new MessageResponse("Login or/and password are not correct"));
        }
    }

    @Operation(summary = "Create new user", description = "Create new user with unique email and a password")
    @PostMapping(path = "/signUp")
    public ResponseEntity<?> CreateAccount(@RequestBody CreateUserRequest createUserRequest) {
        if (createUserRequest.getEmail().isEmpty() || createUserRequest.getPassword().isEmpty()
                || createUserRequest.getUsername().isEmpty()) {
            return ResponseEntity.status(300).body(new MessageResponse("Not all arguments are given"));
        }
        try {
            User newUser = new User(createUserRequest.getEmail(),
                    passwordEncoder.encode(createUserRequest.getPassword()), createUserRequest.getUsername());
            userService.save(newUser);
            return ResponseEntity.status(201).body(new MessageResponse("Your account is created"));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(new MessageResponse("This account already exist"));
        }
    }
}
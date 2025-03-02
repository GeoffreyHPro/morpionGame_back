package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.payload.UpdatePassword;
import com.example.demo.reponses.UserResponse;
import com.example.demo.repository.userRepository.UserRepository;
import com.example.demo.repository.userRepository.UserRepositoryImpl;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "Authorization")
@Api(tags = "User", description = "Endpoint")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity getUsers(Authentication authentication) {

        try {
            User user = userRepo.findByEmail(authentication.getName());
            
            UserResponse r = new UserResponse(user.getUsername(), "bonjour");

            return ResponseEntity.status(200).body(r);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("no informations");
        }
    }

    @PatchMapping
    public ResponseEntity<String> updateUserPassword(
            Authentication authentication,
            @RequestBody UpdatePassword updatePassword) {
        User user = userRepo.findByEmail(authentication.getName());

        userRepositoryImpl.changePassword(user, passwordEncoder.encode(updatePassword.getNewPassword()));
        return ResponseEntity.status(200).body("Password updated");
    }

}

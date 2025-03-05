package com.example.demo.request.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequest {
    private String email;
    private String password;
    private String username;

    public CreateUserRequest(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
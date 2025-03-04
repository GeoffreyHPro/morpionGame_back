package com.example.demo.request;

import lombok.Data;

@Data
public class EmailPasswordRequest {
    private String email;
    private String password;

    public EmailPasswordRequest(String emailParam, String passworParam) {
        this.email = emailParam;
        this.password = passworParam;
    }
}
package com.example.demo.request;

import lombok.Data;

@Data
public class UpdatePassword {
    private String password;
    private String newPassword;

    public UpdatePassword(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }
}
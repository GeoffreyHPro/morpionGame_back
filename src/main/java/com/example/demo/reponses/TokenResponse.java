package com.example.demo.reponses;

import lombok.Data;

@Data
public class TokenResponse {
    private String token;
    private String role;

    public TokenResponse(String token, String role){
        this.role = role;
        this.token = token;
    }
}

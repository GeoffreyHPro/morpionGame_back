package com.example.demo.reponses;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}

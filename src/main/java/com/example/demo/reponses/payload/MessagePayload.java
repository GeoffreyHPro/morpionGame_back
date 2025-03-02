package com.example.demo.reponses.payload;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class MessagePayload {
    
    private String message;
    public MessagePayload(String message){
        this.message = message;
    }
}

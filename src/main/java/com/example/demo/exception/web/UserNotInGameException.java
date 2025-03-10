package com.example.demo.exception.web;

public class UserNotInGameException extends Exception {
    public UserNotInGameException() {
        super("User is not in game");
    }
}

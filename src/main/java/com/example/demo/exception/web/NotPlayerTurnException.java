package com.example.demo.exception.web;

public class NotPlayerTurnException extends Exception {
    public NotPlayerTurnException() {
        super("Not player turn");
    }
}

package com.example.demo.exception;

public class AlreadyExistException extends Exception {
    public AlreadyExistException() {
        super("Ressource already exist");
    }
}
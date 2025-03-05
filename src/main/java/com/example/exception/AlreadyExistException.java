package com.example.exception;

public class AlreadyExistException extends Exception {
    public AlreadyExistException() {
        super("Ressource already exist");
    }
}
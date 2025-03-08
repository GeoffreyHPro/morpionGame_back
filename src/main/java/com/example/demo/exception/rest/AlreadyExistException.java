package com.example.demo.exception.rest;

public class AlreadyExistException extends Exception {
    public AlreadyExistException() {
        super("Ressource already exist");
    }
}
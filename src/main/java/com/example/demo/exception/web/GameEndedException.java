package com.example.demo.exception.web;

public class GameEndedException extends Exception {
    public GameEndedException() {
        super("The game is finished");
    }
}

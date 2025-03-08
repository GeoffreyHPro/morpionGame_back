package com.example.demo.exception.web;

public class PlayersNotReadyException extends Exception {
    public PlayersNotReadyException() {
        super("The players aren't ready");
    }
}

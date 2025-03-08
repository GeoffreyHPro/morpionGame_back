package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.service.web.Game;
import com.example.demo.service.web.StateGame;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        this.game = new Game("user1", "user2");
    }

    @Test
    public void testInitGame() {
        assertEquals("user1", this.game.getUsername1());
        assertEquals("user2", this.game.getUsername2());
        assertEquals(StateGame.NOT_STARTED.toString(), this.game.getStateGame());
    }

}

package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.exception.web.GameEndedException;
import com.example.demo.exception.web.NotPlayerTurnException;
import com.example.demo.exception.web.PlayersNotReadyException;
import com.example.demo.exception.web.UserNotInGameException;
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
        assertEquals(0, this.game.getCells()[0][0]);
    }

    @Test
    public void playersReady() throws UserNotInGameException {
        assertEquals(false, this.game.PlayersAreReady());
        this.game.setUsernameIsReady("user1");
        assertEquals(false, this.game.PlayersAreReady());
        this.game.setUsernameIsReady("user2");
        assertEquals(true, this.game.PlayersAreReady());
    }

    @Test
    public void playersReadyThrowException() {
        assertThrows(UserNotInGameException.class, () -> {
            this.game.setUsernameIsReady("userNotInGame");
        });
    }

    @Test
    public void startGame() throws UserNotInGameException, PlayersNotReadyException {
        assertEquals(StateGame.NOT_STARTED.toString(), this.game.getStateGame());
        this.game.setUsernameIsReady("user1");
        this.game.setUsernameIsReady("user2");
        this.game.startGame();
        assertEquals(StateGame.IN_PROGRESS.toString(), this.game.getStateGame());
        assertTrue(this.game.getPlayerTurn().equals("user1") || this.game.getPlayerTurn().equals("user2"));
    }

    @Test
    public void PlayerTurn()
            throws UserNotInGameException, PlayersNotReadyException, NotPlayerTurnException, GameEndedException {
        this.game.setUsernameIsReady("user1");
        this.game.setUsernameIsReady("user2");
        String playerTurn = this.game.startGame();
        playerTurn = this.game.turn(playerTurn, 0, 0);
        playerTurn = this.game.turn(playerTurn, 1, 1);
    }

    @Test
    public void GameEnded()
            throws UserNotInGameException, PlayersNotReadyException, NotPlayerTurnException, GameEndedException {
        assertThrows(GameEndedException.class, () -> {
            this.game.setUsernameIsReady("user1");
            this.game.setUsernameIsReady("user2");
            String playerTurn = this.game.startGame();
            playerTurn = this.game.turn(playerTurn, 0, 0);
            playerTurn = this.game.turn(playerTurn, 1, 1);
            playerTurn = this.game.turn(playerTurn, 0, 1);
            playerTurn = this.game.turn(playerTurn, 1, 1);
            playerTurn = this.game.turn(playerTurn, 0, 2);
        });
    }
}
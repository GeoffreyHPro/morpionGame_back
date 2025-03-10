package com.example.demo.service.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.demo.exception.web.GameEndedException;
import com.example.demo.exception.web.NotPlayerTurnException;
import com.example.demo.exception.web.PlayersNotReadyException;
import com.example.demo.exception.web.UserNotInGameException;

@Service
public class GameManager {
    private Map<String, Game> games = new ConcurrentHashMap<>();

    public void createGame(String roomId, String username1, String username2) {
        games.put(roomId, new Game(username1, username2));
    }

    public void userIsReady(String roomId, String username) throws UserNotInGameException {
        if (games.containsKey(roomId)) {
            games.get(roomId).setUsernameIsReady(username);
        }
    }

    public String turn(String roomId, String x, String y)
            throws NumberFormatException, NotPlayerTurnException, GameEndedException {
        if (!games.containsKey(roomId)) {
            return games.get(roomId).turn(roomId, Integer.parseInt(x), Integer.parseInt(y));
        }
        return null;
    }

    public boolean playersAreReady(String roomId) {
        if (games.containsKey(roomId)) {
            return games.get(roomId).PlayersAreReady();
        }
        return false;
    }

    public void startGame(String roomId) throws PlayersNotReadyException {
        if (!games.containsKey(roomId)) {
            games.get(roomId).startGame();
        }
    }

    public boolean endGame(String roomId) {
        if (!games.containsKey(roomId)) {
            return games.get(roomId).isFinish();
        }
        return false;
    }
}
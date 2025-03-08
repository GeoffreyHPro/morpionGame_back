package com.example.demo.service.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class GameManager {
    private Map<String, Game> games = new ConcurrentHashMap<>();

    public void createGame(String roomId, String username1, String username2) {
        games.put(roomId, new Game(username1, username2));
    }

    public void userIsReady(String roomId, String username) {
        if (!games.containsKey(roomId)) {
            games.get(roomId).setUsernameIsReady(username);
        }
    }

    public boolean playersAreReady(String roomId) {
        if (!games.containsKey(roomId)) {
            return games.get(roomId).PlayersAreReady();
        }
        return false;
    }

    public void startGame(String roomId) {
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
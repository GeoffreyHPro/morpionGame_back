package com.example.demo.service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class RoomManager {
    private Map<String, Set<String>> rooms = new ConcurrentHashMap<>();

    public void joinRoom(String roomId, String username) {
        if (!rooms.containsKey(roomId)) {
            rooms.put(roomId, ConcurrentHashMap.newKeySet());
        }
        if (rooms.get(roomId).size() < 2) {
            rooms.get(roomId).add(username);
        }
    }

    public void leaveRoom(String roomId, String username) {
        if (rooms.containsKey(roomId)) {
            rooms.get(roomId).remove(username);
            if (rooms.get(roomId).isEmpty()) {
                rooms.remove(roomId);
            }
        }
    }

    public Set<String> getUsersInRoom(String roomId) {
        return rooms.getOrDefault(roomId, Set.of());
    }

    public Map<String, Set<String>> getAllRooms() {
        return rooms;
    }
}

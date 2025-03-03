package com.example.demo.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.service.RoomManager;

@Controller
public class RoomController {

    @Autowired
    private RoomManager roomManager;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room/join")
    public void joinRoom(Map<String, String> payload) {
        System.out.println(payload.get("roomId"));
        System.out.println(payload.get("username"));
        String roomId = payload.get("roomId");
        String username = payload.get("username");
        roomManager.joinRoom(roomId, username);
        messagingTemplate.convertAndSend("/room/" + roomId, username + " a rejoint la room !");
    }

    @MessageMapping("/room/leave")
    public void leaveRoom(Map<String, String> payload) {
        String roomId = payload.get("roomId");
        String username = payload.get("username");
        roomManager.leaveRoom(roomId, username);
        messagingTemplate.convertAndSend("/room/" + roomId, username + " a quitté la room !");
    }

    @MessageMapping("/room/message")
    public void sendMessage(Map<String, String> payload) {
        String roomId = payload.get("roomId");
        String username = payload.get("username");
        String message = payload.get("message");
        messagingTemplate.convertAndSend("/room/" + roomId, username + " : " + message);
    }

    @MessageMapping("/room/list")
    @SendTo("/room/list")
    public Map<String, Set<String>> listRooms() {
        return roomManager.getAllRooms();
    }
}
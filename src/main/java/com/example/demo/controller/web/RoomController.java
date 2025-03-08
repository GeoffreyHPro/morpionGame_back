package com.example.demo.controller.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.request.web.RoomIdUsernameRequest;
import com.example.demo.service.web.GameManager;
import com.example.demo.service.web.RoomManager;

@Controller
public class RoomController {

    @Autowired
    private RoomManager roomManager;

    @Autowired
    private GameManager gameManager;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room/join")
    public void joinRoom(RoomIdUsernameRequest userRoomRequest, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        roomManager.joinRoom(userRoomRequest.getRoomId(), userRoomRequest.getUsername());
        simpMessageHeaderAccessor.getSessionAttributes().put("roomId", userRoomRequest.getRoomId());

        if (roomManager.getUsersInRoom(userRoomRequest.getRoomId()).size() == 2) {
            List<String> listUsers = roomManager.getUsersInRoom(userRoomRequest.getRoomId()).stream().toList();
            gameManager.createGame(userRoomRequest.getRoomId(), listUsers.get(0), listUsers.get(1));
        }

        messagingTemplate.convertAndSend("/room/list", roomManager.getAllRooms());
    }

    @MessageMapping("/room/create")
    public void createRoom(RoomIdUsernameRequest userRoomRequest) {
        roomManager.joinRoom(userRoomRequest.getRoomId(), userRoomRequest.getUsername());
        messagingTemplate.convertAndSend("/room/list", roomManager.getAllRooms());
    }

    @MessageMapping("/room/leave")
    public void leaveRoom(RoomIdUsernameRequest userRoomRequest) {
        roomManager.leaveRoom(userRoomRequest.getRoomId(), userRoomRequest.getUsername());
        messagingTemplate.convertAndSend("/room/list", roomManager.getAllRooms());
    }

    @MessageMapping("/room/{roomId}/message")
    public void sendMessage(Map<String, String> payload, @DestinationVariable String roomId) {
        System.out.println(payload.get("message"));
        messagingTemplate.convertAndSend("/room/" + roomId + "/message", payload);
    }

    @MessageMapping("/room/list")
    @SendTo("/room/list")
    public Map<String, Set<String>> listRooms() {
        return roomManager.getAllRooms();
    }

    @MessageMapping("/room/{roomId}/game/ready")
    public void userIsReady(@DestinationVariable String roomId, Map<String, String> payload) {
        System.out.println(payload.get("username"));
        gameManager.userIsReady(roomId, payload.get("username"));
        if (gameManager.playersAreReady(roomId)) {
            messagingTemplate.convertAndSend("/room/" + roomId + "/game", "game started");
        }
    }
}
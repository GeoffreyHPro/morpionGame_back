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

import com.example.demo.exception.web.UserNotInGameException;
import com.example.demo.reponses.GameResponse;
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
    public void joinRoom(RoomIdUsernameRequest userRoomRequest, SimpMessageHeaderAccessor simpMessageHeaderAccessor)
            throws InterruptedException {
        roomManager.joinRoom(userRoomRequest.getRoomId(), userRoomRequest.getUsername());
        simpMessageHeaderAccessor.getSessionAttributes().put("roomId", userRoomRequest.getRoomId());

        if (roomManager.getUsersInRoom(userRoomRequest.getRoomId()).size() == 2) {
            List<String> listUsers = roomManager.getUsersInRoom(userRoomRequest.getRoomId()).stream().toList();
            System.out.println(listUsers.get(0));
            System.out.println(listUsers.get(1));
            gameManager.createGame(userRoomRequest.getRoomId(), listUsers.get(0), listUsers.get(1));
            Thread.sleep(1500);
            messagingTemplate.convertAndSend("/room/" + userRoomRequest.getRoomId() + "/game",
                    new GameResponse("init_game", ""));
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
    public void userIsReady(@DestinationVariable String roomId, Map<String, String> payload)
            throws UserNotInGameException {
        System.out.println("ready");
        gameManager.userIsReady(roomId, payload.get("username"));
        System.out.println(gameManager.playersAreReady(roomId));
        if (gameManager.playersAreReady(roomId)) {
            System.out.println("users -- ready");
            messagingTemplate.convertAndSend("/room/" + roomId + "/game", new GameResponse("ready", ""));
        }
    }

    @MessageMapping("/room/{roomId}/game/turn")
    public void GameTurn(@DestinationVariable String roomId, Map<String, String> payload)
            throws UserNotInGameException {
        System.out.println(payload.get("username"));
        System.out.println(payload.get("type"));
        try {
            String usernameTurn = gameManager.turn(roomId, payload.get("x"), payload.get("y"));
            messagingTemplate.convertAndSend("/room/" + roomId + "/game", payload.get("type"));
            messagingTemplate.convertAndSend("/room/" + roomId + "/game", "game started");
        } catch (Exception e) {
        }
    }
}
package com.example.demo.controller.web;

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
import com.example.demo.service.web.RoomManager;

@Controller
public class RoomController {

    @Autowired
    private RoomManager roomManager;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room/join")
    public void joinRoom(RoomIdUsernameRequest userRoomRequest, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        roomManager.joinRoom(userRoomRequest.getRoomId(), userRoomRequest.getUsername());

        simpMessageHeaderAccessor.getSessionAttributes().put("roomId", userRoomRequest.getRoomId());

        messagingTemplate.convertAndSend("/room/" + userRoomRequest.getRoomId(),
                userRoomRequest.getUsername() + " a rejoint la room !");
        messagingTemplate.convertAndSend("/room/list", roomManager.getAllRooms());
    }

    @MessageMapping("/room/leave")
    public void leaveRoom(RoomIdUsernameRequest userRoomRequest) {
        roomManager.leaveRoom(userRoomRequest.getRoomId(), userRoomRequest.getUsername());
        messagingTemplate.convertAndSend("/room/" + userRoomRequest.getRoomId(),
                userRoomRequest.getUsername() + " a quitté la room !");
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
}
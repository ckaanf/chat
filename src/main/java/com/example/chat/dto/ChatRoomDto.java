package com.example.chat.dto;

import com.example.chat.entity.ChatRoom;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

public record ChatRoomDto(
        String roomId,
        String name,
        Set<WebSocketSession> sessions
) {
    public ChatRoomDto from(ChatRoom entity){
        return new ChatRoomDto(
                entity.getRoomId(),
                entity.getName(),
                entity.getSessions()
        );
    }
}

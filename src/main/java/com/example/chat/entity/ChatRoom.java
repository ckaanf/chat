package com.example.chat.entity;

import com.example.chat.constant.MessageType;
import com.example.chat.service.ChatService;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();


    public static ChatRoom of (String roomId, String name, Set<WebSocketSession> sessions){
        return new ChatRoom(roomId, name, sessions);
    }

    @Builder
    public ChatRoom(String roomId,String name){
        this.roomId = roomId;
        this.name = name;

    }

    public void handleActions( WebSocketSession session, ChatMessage chatMessage, ChatService chatService){
        if(chatMessage.getType().equals(MessageType.ENTER)){
            sessions.add(session);
            chatMessage.updateMessage(chatMessage.getSender() + "님이 입장하셨습니다. ");
        }
        sendMessage(chatMessage, chatService);
    }

    public<T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}

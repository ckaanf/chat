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
    // 기본적으로 Java에서는 디폴트 값으로 생성하는 것을 지원하지 않지만, 빌더 패턴을 사용 시 디폴트 값을 지정하여 생성할 수 있는 것처럼 구현 가능
    // 따라서 Set 을 초기화 하는 것을 디폴트 값으로 생성했기때문에 @Builder에서는 NULL안 들어가고 팩토리메소드 패턴에서는 null이 들어갈 수 밖에 없었음


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

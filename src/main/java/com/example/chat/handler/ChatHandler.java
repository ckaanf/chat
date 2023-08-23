package com.example.chat.handler;

import com.example.chat.entity.ChatMessage;
import com.example.chat.entity.ChatRoom;
import com.example.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;



    @Override
    protected  void handleTextMessage(WebSocketSession session, TextMessage message) throws  Exception{
        String payload = message.getPayload();
        log.info("payload : " + payload);


        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session,chatMessage,chatService);
    }
    /* Client 가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws  Exception {

        log.info(session + "클라이언트 접속");
    }

    /* Client 가 접속 해제 시 호출되는 메서드*/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        log.info(session + " 클라이언트 접속 해제");
    }
}


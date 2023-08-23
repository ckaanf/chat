package com.example.chat.dto;

import com.example.chat.constant.MessageType;
import com.example.chat.entity.ChatMessage;

public record ChatMessageDto(
        MessageType messageType,
        String roomId,
        String sender,
        String message

) {
    public ChatMessageDto from(ChatMessage entity){
        return new ChatMessageDto(
                entity.getType(),
                entity.getRoomId(),
                entity.getSender(),
                entity.getMessage()
        );
    }
}


package com.example.chat.entity;

import com.example.chat.constant.MessageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatMessage {

     private MessageType type;
     private String roomId;
     private String sender;
     private String message;

    public static ChatMessage of (MessageType type, String roomId,String sender,String message){
        return new ChatMessage(type, roomId, sender, message);
    }

    public void updateMessage(String message){
        this.message = message;
    }

}

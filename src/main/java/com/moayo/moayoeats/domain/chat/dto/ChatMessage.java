package com.moayo.moayoeats.domain.chat.dto;

public record ChatMessage(MessageType messageType, String roomId, String sender, String message) {

    public enum MessageType {
        ENTER, TALK
    }

}
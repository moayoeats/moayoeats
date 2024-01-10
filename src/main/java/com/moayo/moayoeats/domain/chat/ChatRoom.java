package com.moayo.moayoeats.domain.chat;

import com.moayo.moayoeats.domain.chat.dto.ChatMessage;
import com.moayo.moayoeats.domain.chat.service.ChatService;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class ChatRoom {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        ChatMessage msg = chatMessage;
        if (chatMessage.messageType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            msg = new ChatMessage(chatMessage.messageType(), chatMessage.roomId(),
                chatMessage.sender(), chatMessage.sender() + "님이 입장했습니다.");
        }
        sendMessage(msg, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }

}

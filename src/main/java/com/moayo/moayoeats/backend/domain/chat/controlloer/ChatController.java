package com.moayo.moayoeats.backend.domain.chat.controlloer;

import com.moayo.moayoeats.backend.domain.chat.dto.ChatMessage;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    @MessageMapping("/chats/join")
    @SendTo("/sub/chats/room/{postId}")
    public ChatMessage join(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @DestinationVariable(value = "postId") Long postId
    ) {
        String username = userDetails.getUsername();

        return new ChatMessage(postId, username + "님이 입장하셨습니다.", username);
    }

    @MessageMapping("/chats/message")
    @SendTo("/sub/chats/room/{postId}")
    public ChatMessage message(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @DestinationVariable(value = "postId") Long postId,
        ChatMessage message
    ) {
        String username = userDetails.getUsername();
        String formattedContent = username + " : " + message.content();

        return new ChatMessage(postId, formattedContent, username);
    }

}
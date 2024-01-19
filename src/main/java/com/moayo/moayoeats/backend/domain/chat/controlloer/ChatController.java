package com.moayo.moayoeats.backend.domain.chat.controlloer;

import com.moayo.moayoeats.backend.domain.chat.dto.ChatMessageDTO;
import com.moayo.moayoeats.backend.domain.chat.service.ChatMessageService;
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

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chats/join")
    @SendTo("/sub/chats/room/{postId}")
    public ChatMessageDTO join(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @DestinationVariable(value = "postId") String postId
        ) {
        String username = userDetails.getUsername();

        return chatMessageService.saveChatMessage(postId, username + "님이 입장하셨습니다.", username, userDetails.getUser());
    }

    @MessageMapping("/chats/message")
    @SendTo("/sub/chats/room/{postId}")
    public ChatMessageDTO message(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @DestinationVariable(value = "postId") String postId,
        ChatMessageDTO message
    ) {
        String username = userDetails.getUsername();
        String formattedContent = username + " : " + message.content();

        return chatMessageService.saveChatMessage(postId, formattedContent, username, userDetails.getUser());
    }

}
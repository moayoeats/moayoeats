package com.moayo.moayoeats.backend.domain.chat.controlloer;

import com.moayo.moayoeats.backend.domain.chat.dto.response.ChatMessageResponse;
import com.moayo.moayoeats.backend.domain.chat.dto.request.ChatMessageRequest;
import com.moayo.moayoeats.backend.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chats/join/{postId}")
    @SendTo("/sub/chats/room/{postId}")
    public ChatMessageResponse join(
        @DestinationVariable(value = "postId") String postId,
        ChatMessageRequest req
    ) {
        String content = req.sender() + "님이 입장하셨습니다.";

        return new ChatMessageResponse(content);
    }

    @MessageMapping("/chats/message/{postId}")
    @SendTo("/sub/chats/room/{postId}")
    public ChatMessageResponse message(
        @DestinationVariable(value = "postId") String postId,
        ChatMessageRequest req
    ) {
        String content = req.content();
        String username = req.sender();

//        chatMessageService.saveChatMessage(postId, username, content);

        return new ChatMessageResponse(content);
    }

}
package com.moayo.moayoeats.backend.domain.chat.controlloer;

import com.moayo.moayoeats.backend.domain.chat.dto.request.ChatMessageRequest;
import com.moayo.moayoeats.backend.domain.chat.dto.response.ChatMessageResponse;
import com.moayo.moayoeats.backend.domain.chat.entity.ChatMessage;
import com.moayo.moayoeats.backend.domain.chat.service.impl.ChatMessageServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatMessageServiceImpl chatMessageService;

    @MessageMapping("/chats/join/{postId}")
    @SendTo("/sub/chats/room/{postId}")
    public ChatMessageResponse join(
        @DestinationVariable(value = "postId") String postId,
        ChatMessageRequest req
    ) {
        String content = "님이 입장하셨습니다.";
        String username = req.sender();

        chatMessageService.saveChatMessage(postId, username, content);

        return new ChatMessageResponse(content, username);
    }

    @MessageMapping("/chats/message/{postId}")
    @SendTo("/sub/chats/room/{postId}")
    public ChatMessageResponse message(
        @DestinationVariable(value = "postId") String postId,
        ChatMessageRequest req
    ) {
        String content = req.content();
        String username = req.sender();

        chatMessageService.saveChatMessage(postId, username, content);

        return new ChatMessageResponse(content, username);
    }

    @GetMapping("/chats/history/{postId}")
    @ResponseBody
    public List<ChatMessageResponse> getChatHistory(@PathVariable String postId) {
        return chatMessageService.getChatHistory(postId);
    }

}
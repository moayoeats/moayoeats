package com.moayo.moayoeats.backend.domain.chat.controlloer;

import com.moayo.moayoeats.backend.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chats/exists/{postId}")
    public ResponseEntity<Boolean> checkChatRoomExists(@PathVariable Long postId) {
        boolean exists = chatRoomService.existsById(postId);
        return ResponseEntity.ok(exists);
    }

}
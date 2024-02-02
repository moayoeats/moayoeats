package com.moayo.moayoeats.frontend.domain.chat;

import com.moayo.moayoeats.backend.domain.chat.service.ChatRoomService;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatFrontController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chat/{postId}")
    public String getChatPage(
            @PathVariable Long postId,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails
        ) {
        if (!chatRoomService.existsById(postId)) {
            return "redirect:/";
        }

        model.addAttribute("postId", postId);
        model.addAttribute("username", userDetails.getUsername());
        return "domain/chat/chatroom";
    }

}
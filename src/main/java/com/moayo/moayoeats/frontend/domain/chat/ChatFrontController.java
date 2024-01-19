package com.moayo.moayoeats.frontend.domain.chat;

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

    @GetMapping("/chat/{postId}")
    public String getChatPage(
            @PathVariable Long postId,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails
        ) {
        model.addAttribute("postId", postId);
        model.addAttribute("username", userDetails.getUsername()); // 현재 로그인된 사용자 이름
        return "domain/chat/chatroom"; // Thymeleaf 템플릿 이름
    }

}
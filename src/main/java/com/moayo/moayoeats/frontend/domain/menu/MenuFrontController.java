package com.moayo.moayoeats.frontend.domain.menu;

import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MenuFrontController {


    @GetMapping("/menu/{postId}")
    public String menuPage(
        @PathVariable(name = "postId") Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        Model model
    ) {

        model.addAttribute("userId", userDetails.getUser().getId());
        model.addAttribute("postId", postId);
        
        return "domain/menu/menu";
    }
}
package com.moayo.moayoeats.frontend.domain.offer;

import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OfferFrontController {

    @GetMapping("/post/{postId}/offer")
    public String getOffer(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

        model.addAttribute("postId", postId);
        model.addAttribute("userId", userDetails.getUser().getId());

        return "domain/offer/get-offer";
    }
}

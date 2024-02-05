package com.moayo.moayoeats.frontend.domain.review;

import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReviewFrontController {

    @GetMapping("/createreview/{orderId}")
    public String createReviewPage(
        @PathVariable(name = "orderId") Long orderId,
        @AuthenticationPrincipal UserDetailsImpl userDetails, Model model
    ) {

        model.addAttribute("userId", userDetails.getUser().getId());
        model.addAttribute("orderId", orderId);
        
        return "domain/review/createreview";
    }

}

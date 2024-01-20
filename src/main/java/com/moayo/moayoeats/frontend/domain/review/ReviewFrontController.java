package com.moayo.moayoeats.frontend.domain.review;

import lombok.RequiredArgsConstructor;
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
        Model model
    ) {
        model.addAttribute("orderId", orderId);
        return "domain/review/createreview";
    }

}

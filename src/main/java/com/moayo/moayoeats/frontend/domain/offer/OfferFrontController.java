package com.moayo.moayoeats.frontend.domain.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OfferFrontController {

    @GetMapping("/post/{postId}/offer")
    public String getOffer(@PathVariable Long postId, Model model) {

        model.addAttribute("postId", postId);

        return "/domain/offer/get-offer";
    }
}

package com.moayo.moayoeats.frontend.domain.menu;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MenuFrontController {


    @GetMapping("/menu/{postId}")
    public String menuPage(
        @PathVariable(name = "postId") Long postId,
        Model model
    ) {
        model.addAttribute("postId", postId);
        return "domain/menu/menu";
    }
}
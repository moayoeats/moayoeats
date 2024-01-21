package com.moayo.moayoeats.frontend.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/index")
    public String indexPage() {
        return "index.html";
    }
}

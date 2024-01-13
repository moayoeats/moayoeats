package com.moayo.moayoeats.frontend.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class PostFrontController {

    @GetMapping("/test/createpost")
    public String createPostPage(){
        return "domain/post/createpost";
    }
}

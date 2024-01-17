package com.moayo.moayoeats.frontend.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/moayo")
public class PostFrontController {

    @GetMapping("/createpost")
    public String createPostPage() {
        return "domain/post/createpost";
    }

    @GetMapping("/readpost/{postId}")
    public String readpostPage(@PathVariable(name = "postId") Long postId) {
        ModelAndView mav = new ModelAndView("postId");
        mav.addObject("postId", postId);
        return "domain/post/readpost";
    }

    //인증정보 없이 글 전체조회
    @GetMapping("/allposts")
    public String readAllPosts() {
        return "domain/post/allposts";
    }
}

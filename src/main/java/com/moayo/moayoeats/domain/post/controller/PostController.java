package com.moayo.moayoeats.domain.post.controller;

import com.moayo.moayoeats.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.domain.post.service.PostService;
import com.moayo.moayoeats.global.dto.ApiResponse;
import com.moayo.moayoeats.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    // 글 생성하기
    @PostMapping("/posts")
    public ApiResponse<Void> createPost(
        @Valid @RequestBody PostRequest postReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        postService.createPost(postReq,userDetails.getUser());
        return new ApiResponse<>(HttpStatus.CREATED.value(), "글을 생성했습니다.");
    }

}

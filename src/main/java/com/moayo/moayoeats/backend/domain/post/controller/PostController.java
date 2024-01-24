package com.moayo.moayoeats.backend.domain.post.controller;

import com.moayo.moayoeats.backend.domain.post.dto.request.PostIdRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.backend.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.backend.domain.post.exception.validator.Category;
import com.moayo.moayoeats.backend.domain.post.service.PostService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    ) {
        postService.createPost(postReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.CREATED.value(), "글을 생성했습니다.");
    }

    // 인증정보 없이 모든 글 조회하기
    @GetMapping("/readonly/posts/page/{page}")
    public ApiResponse<List<BriefPostResponse>> getPostsForAnyone(
        @PathVariable(name = "page") int page
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "모든 글 조회에 성공했습니다.",
            postService.getPostsForAnyone(page));
    }

    // 모든 글 조회하기
    @GetMapping("/posts/page/{page}")
    public ApiResponse<List<BriefPostResponse>> getPosts(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "page") int page
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "모든 글 조회에 성공했습니다.",
            postService.getPosts(page, userDetails.getUser()));
    }

    //인증정보 없이 글 단독 조회
    @GetMapping("/readonly/posts/{postId}")
    public ApiResponse<DetailedPostResponse> getPostTest(
        @PathVariable(name = "postId") Long postId
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "글 상세페이지 조회에 성공했습니다.",
            postService.getPostForAnyone(postId));
    }

    //글 단독 조회, 글 상세페이지
    @GetMapping("/posts/{postId}")
    public ApiResponse<DetailedPostResponse> getPost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "postId") Long postId
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "글 상세페이지 조회에 성공했습니다.",
            postService.getPost(postId, userDetails.getUser()));
    }

    //인증정보 없이 글 카테고리별 조회
    @GetMapping("/readonly/posts/category/{page}")
    public ApiResponse<List<BriefPostResponse>> getPostsByCategoryForAnyone(
        @PathVariable(name = "page") int page,
        @RequestParam @Category String category
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "글 카테고리별 조회에 성공했습니다.",
            postService.getPostsByCategoryForAnyone(page, category));
    }

    //글 카테고리별 조회
    @GetMapping("/posts/category/{page}")
    public ApiResponse<List<BriefPostResponse>> getPostsByCategory(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "page") int page,
        @RequestParam @Category String category
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "글 카테고리별 조회에 성공했습니다.",
            postService.getPostsByCategory(page, category, userDetails.getUser()));
    }

    //인증정보 없이 글 검색하기
    @GetMapping("/readonly/posts/search/{page}")
    public ApiResponse<List<BriefPostResponse>> searchPostForAnyone(
        @PathVariable(name = "page") int page,
        @RequestParam String keyword
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "검색 결과",
            postService.searchPostForAnyone(page, keyword));
    }

    //글 검색하기
    @GetMapping("/posts/search/{page}")
    public ApiResponse<List<BriefPostResponse>> searchPost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "page") int page,
        @RequestParam String keyword
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "검색 결과",
            postService.searchPost(page, keyword, userDetails.getUser()));
    }

    //모집 취소하기
    @DeleteMapping("/posts")
    public ApiResponse<Void> deletePost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody PostIdRequest postIdReq
    ) {
        postService.deletePost(postIdReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "모집 취소에 성공했습니다.");
    }

    //모집 마감
    @PatchMapping("/posts/close")
    public ApiResponse<Void> closeApplication(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody PostIdRequest postIdReq
    ) {
        postService.closeApplication(postIdReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "모집이 마감되었습니다.");
    }

    //주문 완료
    @PatchMapping("/posts/complete-order")
    public ApiResponse<Void> completeOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody PostIdRequest postIdReq
    ) {
        postService.completeOrder(postIdReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "주문완료 처리가 되었습니다.");
    }

    //나가기 기능
    @DeleteMapping("/posts/exit")
    public ApiResponse<Void> exit(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody PostIdRequest postIdReq
    ) {
        postService.exit(postIdReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "글에서 나가기 되었습니다.");
    }

    //수령 완료
    @DeleteMapping("/posts/received")
    public ApiResponse<Void> receiveOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody PostIdRequest postIdReq
    ) {
        postService.receiveOrder(postIdReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "수령완료 처리가 되었습니다.");
    }

}
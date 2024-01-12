package com.moayo.moayoeats.backend.domain.post.service;

import com.moayo.moayoeats.backend.domain.post.dto.request.PostCategoryRequest;
import com.moayo.moayoeats.backend.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.backend.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostIdRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostSearchRequest;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface PostService {

    /**
     * @param postReq : 글 작성에 필요한 dto
     */
    void createPost(PostRequest postReq, User user);

    /**
     * @param user : login info to sort posts by current location
     * @return Lists of brief informations about the post
     */
    List<BriefPostResponse> getPosts(User user);

    /**
     * @param postId : 글 조회에 필요한 postId
     * @param user   : 글 조회자, 나의 메뉴 조회에 필요함
     * @return DetailedPostResponse : 글 상세페이지 조회를 위한 dto
     */
    DetailedPostResponse getPost(Long postId, User user);

    /**
     * @param postCategorySearchReq : 카테고리
     * @param user                  : 글 조회자, 현재 위치 기준으로 정렬하기 위해 필요함
     * @return
     */
    List<BriefPostResponse> getPostsByCategory(PostCategoryRequest postCategorySearchReq,
        User user);

    /**
     * @param postSearchReq : 검색어
     * @param user          : 글 조회자, 현재 위치 기준으로 정렬하기 위해 필요함
     * @return
     */
    List<BriefPostResponse> searchPost(PostSearchRequest postSearchReq, User user);

    /**
     * @param postIdReq : 글을 삭제하기 위한 postId
     * @param user      : 글 삭제자, 글 주인과 대조
     */
    void deletePost(PostIdRequest postIdReq, User user);

    //Test
    public void createPostTest(PostRequest postReq);

}

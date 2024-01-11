package com.moayo.moayoeats.domain.post.service;

import com.moayo.moayoeats.domain.post.dto.request.PostCategoryRequest;
import com.moayo.moayoeats.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.domain.user.entity.User;
import java.util.List;

public interface PostService {

    /**
     *
     * @param postReq : 글 작성에 필요한 dto
     */
    public void createPost(PostRequest postReq, User user);

    /**
     *
     * @param user : login info to sort posts by current location
     * @return Lists of brief informations about the post
     */
    public List<BriefPostResponse> getPosts(User user);

    /**
     *
     * @param postId : 글 조회에 필요한 postId
     * @param user : 글 조회자, 나의 메뉴 조회에 필요함
     * @return DetailedPostResponse : 글 상세페이지 조회를 위한 dto
     */
    public DetailedPostResponse getPost(Long postId, User user);

    /**
     *
     * @param postCategorySearchReq : 카테고리
     * @param user : 글 조회자, 현재 위치 기준으로 정렬하기 위해 필요함
     * @return
     */
    public List<BriefPostResponse> getPostsByCategory(PostCategoryRequest postCategorySearchReq, User user);

}

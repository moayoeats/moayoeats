package com.moayo.moayoeats.backend.domain.post.service;

import com.moayo.moayoeats.backend.domain.post.dto.request.PostIdRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.backend.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface PostService {

    /**
     * 인증정보 없이 전체 글 조회
     *
     * @param page : 몇페이지 조회할건지 int pageNumber
     * @return Lists of brief information about the post
     */
    List<BriefPostResponse> getPostsForAnyone(int page);

    /**
     * @param page : 몇페이지 조회할건지 int pageNumber
     * @param user : 글 조회자
     * @return
     */
    List<BriefPostResponse> getPosts(int page, User user);

    /**
     * @param page   : 몇페이지 조회할건지 int pageNumber
     * @param status : 조회할 글의 상태 PostStatusEnum
     * @param user   : 글 조회자
     * @return
     */
    List<BriefPostResponse> getStatusPosts(int page, String status, User user);

    /**
     * @param postId : 글 조회에 필요한 postId
     * @return
     */
    DetailedPostResponse getPostForAnyone(Long postId);

    /**
     * @param postId : 글 조회에 필요한 postId
     * @param user   : 글 조회자, 나의 메뉴 조회에 필요함
     * @return DetailedPostResponse : 글 상세페이지 조회를 위한 dto
     */
    DetailedPostResponse getPost(Long postId, User user);

    /**
     * @param page     : 페이지 조회에 필요한 int pageNumber
     * @param category : 카테고리
     * @return
     */
    List<BriefPostResponse> getPostsByCategoryForAnyone(int page, String category);

    /**
     * @param category : 카테고리
     * @param user     : 글 조회자, 현재 위치 기준으로 정렬하기 위해 필요함
     * @return
     */
    List<BriefPostResponse> getPostsByCategory(int page, String category, User user);

    /**
     * @param page     : 페이지 조회에 필요한 int pageNumber
     * @param category : 카테고리
     * @param status   : 조회할 글의 상태 PostStatusEnum
     * @param user     : 글 조회자, 현재 위치 기준으로 정렬하기 위해 필요함
     * @return
     */
    List<BriefPostResponse> getStatusPostsByCategory(int page, String category, String status,
        User user);

    /**
     * @param page    : 페이지 조회에 필요한 int pageNumber
     * @param keyword : 검색어
     * @return
     */
    List<BriefPostResponse> searchPostForAnyone(int page, String keyword);

    /**
     * @param keyword : 검색어
     * @param user    : 글 조회자, 현재 위치 기준으로 정렬하기 위해 필요함
     * @return
     */
    List<BriefPostResponse> searchPost(int page, String keyword, User user);

    /**
     * @param page    : 페이지 조회에 필요한 int page
     * @param keyword : 검색어 조회에 필요한 String
     * @param status  : 조회할 글의 상태 PostStatusEnum
     * @param user    : 글 조회자, 현재 위치 기준으로 정렬하기 위해 필요함
     * @return :
     */
    List<BriefPostResponse> searchStatusPost(int page, String keyword, String status, User user);

    /**
     * @param postIdReq : 글을 삭제하기 위한 postId
     * @param user      : 글 삭제자, 글 주인과 대조
     */
    void deletePost(PostIdRequest postIdReq, User user);

    /**
     * @param postIdReq : postId
     * @param user      : 글 작성자
     */
    void closeApplication(PostIdRequest postIdReq, User user);

    /**
     * @param postIdReq : postId
     * @param user      : 글 작성자
     */
    void completeOrder(PostIdRequest postIdReq, User user);

    /**
     * @param postIdReq : postId
     * @param user      : 글에서 나가려는 참가자
     */
    void exit(PostIdRequest postIdReq, User user);

    /**
     * @param postIdReq : postId
     * @param user      : 주문완료 처리를 하려는 참가자
     */
    void receiveOrder(PostIdRequest postIdReq, User user);

}

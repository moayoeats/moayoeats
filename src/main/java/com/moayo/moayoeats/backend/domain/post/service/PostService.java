package com.moayo.moayoeats.backend.domain.post.service;

import com.moayo.moayoeats.backend.domain.post.dto.request.PostIdRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.backend.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface PostService {

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

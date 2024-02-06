package com.moayo.moayoeats.backend.domain.post.service;

import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.user.entity.User;

public interface PostCreateService {

    /**
     * @param postReq : 글 작성에 필요한 dto
     */
    void createPost(PostRequest postReq, User user);

}

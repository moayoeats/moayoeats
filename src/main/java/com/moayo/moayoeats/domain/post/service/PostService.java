package com.moayo.moayoeats.domain.post.service;

import com.moayo.moayoeats.domain.post.dto.request.PostRequest;

public interface PostService {

    /**
     *
     * @param postReq : 글 작성에 필요한 dto
     */
    public void createInvitation(PostRequest postReq);

}

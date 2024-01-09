package com.moayo.moayoeats.domain.post.service;

import com.moayo.moayoeats.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.domain.post.dto.response.BriefPostResponse;
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

}

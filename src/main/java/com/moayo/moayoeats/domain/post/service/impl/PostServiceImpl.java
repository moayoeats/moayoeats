package com.moayo.moayoeats.domain.post.service.impl;

import com.moayo.moayoeats.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.post.repository.PostRepository;
import com.moayo.moayoeats.domain.post.service.PostService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public void createPost(PostRequest postReq){
        //set deadline to mins after
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusMinutes(postReq.deadline().getMins());

        Post post = Post.builder()
            .address(postReq.address())
            .store(postReq.store())
            .deliveryCost(postReq.deliveryCost())
            .minPrice(postReq.minPrice())
            .deadline(deadline)
            .category(postReq.category())
            .build();

        postRepository.save(post);
    }

}

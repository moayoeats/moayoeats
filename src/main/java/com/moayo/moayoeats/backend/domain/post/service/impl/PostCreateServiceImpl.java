package com.moayo.moayoeats.backend.domain.post.service.impl;

import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import com.moayo.moayoeats.backend.domain.post.service.PostCreateService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.backend.domain.userpost.repository.UserPostRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostCreateServiceImpl implements PostCreateService {

    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;

    @Override
    public void createPost(PostRequest postReq, User user) {
        //set deadline to hours and mins after now
        LocalDateTime deadline = LocalDateTime.now()
            .plusMinutes(getIntFromString(postReq.deadlineMins()))
            .plusHours(getIntFromString(postReq.deadlineHours()));

        //get latitude and longitude from the coordinate
        String [] location = getAddress(postReq.address());
        double latitude = Double.valueOf(location[0]);
        double longitude = Double.valueOf(location[1]);

        String category = postReq.category();
        Post post;
        if (checkIfCategoryEnum(category)) {
            //Build new post with the post request dto
            post = Post.builder()
                .latitude(latitude)
                .longitude(longitude)
                .store(postReq.store())
                .deliveryCost(getIntFromString(postReq.deliveryCost()))
                .minPrice(getIntFromString(postReq.minPrice()))
                .deadline(deadline)
                .category(CategoryEnum.valueOf(category))
                .postStatus(PostStatusEnum.OPEN)
                .build();
        } else {
            post = Post.builder()
                .latitude(latitude)
                .longitude(longitude)
                .store(postReq.store())
                .deliveryCost(getIntFromString(postReq.deliveryCost()))
                .minPrice(getIntFromString(postReq.minPrice()))
                .deadline(deadline)
                .cuisine(category)
                .postStatus(PostStatusEnum.OPEN)
                .build();
        }
        //save the post
        postRepository.save(post);

        //Build new relation between the post and the user
        UserPost userpost = UserPost.builder().user(user).post(post).role(UserPostRole.HOST)
            .build();

        //save the relation
        userPostRepository.save(userpost);
    }

    private int getIntFromString(String s) {
        int i = 0;
        if (!s.equals("")) {
            i = Integer.parseInt(s);
        }
        return i;
    }

    private String[] getAddress(String address) {
        address = address.replace("(lat:", "");
        address = address.replace("lng:", "");
        address = address.replace(")", "");
        return address.split(",");
    }

    private boolean checkIfCategoryEnum(String category) {
        for (CategoryEnum c : CategoryEnum.values()) {
            if (c.name().equals(category)) {
                return true;
            }
        }
        return false;
    }
}

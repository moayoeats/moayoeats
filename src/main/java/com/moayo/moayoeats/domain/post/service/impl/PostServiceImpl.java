package com.moayo.moayoeats.domain.post.service.impl;

import com.moayo.moayoeats.domain.menu.entity.Menu;
import com.moayo.moayoeats.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.post.repository.PostRepository;
import com.moayo.moayoeats.domain.post.service.PostService;
import com.moayo.moayoeats.domain.user.entity.User;
import com.moayo.moayoeats.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.domain.userpost.repository.UserPostRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;
    private final MenuRepository menuRepository;

    public void createPost(PostRequest postReq, User user){
        //set deadline to hours and mins after now
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(postReq.deadlineMins()).plusHours(postReq.deadlineHours());

        //Build new post with the post request dto
        Post post = Post.builder()
            .address(postReq.address())
            .store(postReq.store())
            .deliveryCost(postReq.deliveryCost())
            .minPrice(postReq.minPrice())
            .deadline(deadline)
            .category(postReq.category())
            .build();

        //save the post
        postRepository.save(post);

        //Build new relation between the post and the user
        UserPost userpost = UserPost.builder()
            .user(user)
            .post(post)
            .role(UserPostRole.HOST)
            .build();

        //save the relation
        userPostRepository.save(userpost);
    }

    public List<BriefPostResponse> getPosts(User user){
        List<Post> posts = postRepository.findAll();

        List<BriefPostResponse> postResponses =
        posts.stream()
            .map((Post post)-> new BriefPostResponse(
                post.getId(),
                getAuthor(post),
                post.getAddress(),
                post.getStore(),
                post.getMinPrice(),
                getSumPrice(post),
                getDeadline(post)
            )).toList();

        return postResponses;
    }

    private String getAuthor(Post post){
        //the host has been saved in the table only once, when saving a new post, so there is only one host of a post
        UserPost userpost = userPostRepository.findAllByPostAndRoleEquals(post, UserPostRole.HOST).get(0);
        return userpost.getUser().getNickname();
    }

    private int getSumPrice(Post post){
        List<UserPost> userposts = userPostRepository.findAllByPost(post);
        int sumPrice = 0;

        //add all prices from the menus from the users who are participating/hosting a post
        for(UserPost userpost : userposts){
            List<Menu> menus = menuRepository.findAllByUserAndPost(userpost.getUser(),post);
            for(Menu menu : menus){
                sumPrice += menu.getPrice();
            }
        }

        return sumPrice;
    }

    private LocalDateTime getDeadline(Post post){
        //remove nano sencods from the LocalDateTime
        return post.getDeadline().withNano(0);
    }

}

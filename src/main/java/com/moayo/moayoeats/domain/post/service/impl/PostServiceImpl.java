package com.moayo.moayoeats.domain.post.service.impl;

import com.moayo.moayoeats.domain.menu.entity.Menu;
import com.moayo.moayoeats.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.domain.post.repository.PostRepository;
import com.moayo.moayoeats.domain.post.service.PostService;
import com.moayo.moayoeats.domain.user.entity.User;
import com.moayo.moayoeats.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.domain.userpost.exception.UserPostErrorCode;
import com.moayo.moayoeats.domain.userpost.repository.UserPostRepository;
import com.moayo.moayoeats.global.exception.ErrorCode;
import com.moayo.moayoeats.global.exception.GlobalException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
                getAuthor(getUserPostsByPost(post)),
                post.getAddress(),
                post.getStore(),
                post.getMinPrice(),
                getSumPrice(getUserPostsByPost(post),post),
                getDeadline(post)
            )).toList();

        return postResponses;
    }

    @Override
    public DetailedPostResponse getPost(Long postId, User user) {
        Post post = getPostById(postId);
        List<UserPost> userPosts = getUserPostsByPost(post);
        List<List<Menu>> allMenus = getAllMenus(userPosts);

        return DetailedPostResponse.builder()
            .address(post.getAddress())
            .store(post.getStore())
            .minPrice(post.getMinPrice())
            .deliveryCost(post.getDeliveryCost())
            .participants(getParticipants(userPosts))
            .menus(allMenus)
            .myMenus(getMyMenus(user, allMenus))
            .sumPrice(getSumPrice(getUserPostsByPost(post),post))
            .deadline(getDeadline(post))
            .build();
    }

    private String getAuthor(List<UserPost> userPosts){
        for(UserPost userpost : userPosts ){
            if(userpost.getRole().equals(UserPostRole.HOST)){
                return userpost.getUser().getNickname();
            }
        }
        throw new GlobalException(UserPostErrorCode.NOT_FOUND_HOST);
    }

    private int getSumPrice(List<UserPost> userposts, Post post){
        int sumPrice = 0;

        //add all prices from the menus from the users who are participating/hosting a post
        for(UserPost userpost : userposts){
            List<Menu> menus = getUserMenus(userpost.getUser(), post);
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

    private Post getPostById(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new GlobalException(
            PostErrorCode.NOT_FOUND_POST));
        return post;
    }

    private List<UserPost> getUserPostsByPost(Post post){
        return userPostRepository.findAllByPost(post);
    }

    private List<List<Menu>> getAllMenus(List<UserPost> userposts){

        List<List<Menu>> menus = userposts.stream().map((UserPost userpost)->userpost.getPost().getMenus()).toList();
        return menus;
    }

    private List<Menu> getUserMenus(User user, Post post){
        return menuRepository.findAllByUserAndPost(user,post);
    }

    private List<Menu> getMyMenus(User user, List<List<Menu>> allMenus){
        for(List<Menu> menus : allMenus){
            if(menus.size()>0){
                if(menus.get(0).getUser().getId().equals(user.getId())){
                    return menus;
                }
            }
        }
        return null;
    }

    private List<String> getParticipants(List<UserPost> userPosts){
        List<String> participants = new ArrayList<>();
        for(UserPost userpost : userPosts){
            participants.add(userpost.getUser().getNickname());
        }
        return participants;
    }

}

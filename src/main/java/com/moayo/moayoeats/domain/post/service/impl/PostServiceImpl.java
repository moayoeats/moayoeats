package com.moayo.moayoeats.domain.post.service.impl;

import com.moayo.moayoeats.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.domain.menu.dto.response.NickMenusResponse;
import com.moayo.moayoeats.domain.menu.entity.Menu;
import com.moayo.moayoeats.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.domain.post.dto.request.PostCategoryRequest;
import com.moayo.moayoeats.domain.post.dto.request.PostIdRequest;
import com.moayo.moayoeats.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.domain.post.repository.PostRepository;
import com.moayo.moayoeats.domain.post.service.PostService;
import com.moayo.moayoeats.domain.user.entity.User;
import com.moayo.moayoeats.domain.user.repository.UserRepository;
import com.moayo.moayoeats.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.domain.userpost.exception.UserPostErrorCode;
import com.moayo.moayoeats.domain.userpost.repository.UserPostRepository;
import com.moayo.moayoeats.global.exception.GlobalException;
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
    private final UserRepository userRepository;//Test

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
        List<Post> posts = findAll();
        return postsToBriefResponses(posts);
    }

    @Override
    public DetailedPostResponse getPost(Long postId, User user) {
        Post post = getPostById(postId);
        List<UserPost> userPosts = getUserPostsByPost(post);

        return DetailedPostResponse.builder()
            .address(post.getAddress())
            .store(post.getStore())
            .minPrice(post.getMinPrice())
            .deliveryCost(post.getDeliveryCost())
            .menus(getNickMenus(userPosts))
            .sumPrice(getSumPrice(getUserPostsByPost(post),post))
            .deadline(getDeadline(post))
            .build();
    }

    @Override
    public List<BriefPostResponse> getPostsByCategory(
        PostCategoryRequest postCategoryReq,
        User user
    ) {
        List<Post> posts;
        if(postCategoryReq.category().equals(CategoryEnum.ALL.toString())){
            posts = findAll();
        }else{
            posts = postRepository.findAllByCategoryEquals(postCategoryReq.category()).orElse(null);
        }
        return postsToBriefResponses(posts);
    }

    @Override
    public void deletePost(PostIdRequest postIdReq, User user) {
        //check if the post exists
        Post post = getPostById(postIdReq.postId());
        //check if the user has a relation with the post
        List<UserPost> userPosts = getUserPostsByPost(post);
        //check if the user is the host of the post
        User host = getAuthor(userPosts);
        if(!host.getId().equals(user.getId())){
            throw new GlobalException(PostErrorCode.FORBIDDEN_ACCESS);
        }

        userPostRepository.deleteAll(userPosts);
        postRepository.delete(post);
    }

    private List<Post> findAll(){
        return postRepository.findAll();
    }

    private List<BriefPostResponse> postsToBriefResponses(List<Post> posts){
        return posts.stream()
                .map((Post post)-> new BriefPostResponse(
                    post.getId(),
                    getAuthor(getUserPostsByPost(post)).getNickname(),
                    post.getAddress(),
                    post.getStore(),
                    post.getMinPrice(),
                    getSumPrice(getUserPostsByPost(post),post),
                    getDeadline(post)
                )).toList();
    }

    private User getAuthor(List<UserPost> userPosts){
        for(UserPost userpost : userPosts ){
            if(userpost.getRole().equals(UserPostRole.HOST)){
                return userpost.getUser();
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

    private List<NickMenusResponse> getNickMenus(List<UserPost> userposts){

        List<NickMenusResponse> menus =
            //List<UserPost> -> List<NickMenusResponse>
            userposts.stream().map((UserPost userpost)->
            new NickMenusResponse(userpost.getUser().getNickname(),
                //List<Menu> menus -> List<MenuResponse>
                getUserMenus(userpost.getUser(),userpost.getPost()).stream().map((Menu menu)->new MenuResponse(menu.getMenuname(),menu.getPrice())).toList()
            )).toList();
        return menus;
    }

    private List<Menu> getUserMenus(User user, Post post){
        return menuRepository.findAllByUserAndPost(user,post);
    }

    //Test
    public void createPostTest(PostRequest postReq){
        //set fake user
        Long l = 1L;
        User user = userRepository.findById(l).orElse(null);

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

}

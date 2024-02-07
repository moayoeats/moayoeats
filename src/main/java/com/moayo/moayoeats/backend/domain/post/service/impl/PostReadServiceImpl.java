package com.moayo.moayoeats.backend.domain.post.service.impl;

import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.menu.dto.response.NickMenusResponse;
import com.moayo.moayoeats.backend.domain.menu.entity.Menu;
import com.moayo.moayoeats.backend.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.backend.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.backend.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.backend.domain.post.repository.PostCustomRepository;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import com.moayo.moayoeats.backend.domain.post.service.PostReadService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.backend.domain.userpost.exception.UserPostErrorCode;
import com.moayo.moayoeats.backend.domain.userpost.repository.UserPostRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReadServiceImpl implements PostReadService {

    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;
    private final PostCustomRepository postCustomRepository;
    private final MenuRepository menuRepository;
    private static final int pagesize = 5;

    @Override
    public List<BriefPostResponse> getPostsForAnyone(int page) {
        List<Post> posts = findPage(page);
        return postsToBriefResponses(posts);
    }

    @Override
    public List<BriefPostResponse> getPosts(int page, User user) {
        return getAllPosts(page, user);
    }

    @Override
    public List<BriefPostResponse> getStatusPosts(int page, String status, User user) {
        if(status.equals("ALL")){
            return getAllPosts(page, user);
        }else{
            PostStatusEnum statusEnum = PostStatusEnum.valueOf(status);
            return getAllStatusPosts(page, statusEnum, user);
        }
    }

    @Override
    public DetailedPostResponse getPostForAnyone(Long postId) {
        Post post = getPostById(postId);
        List<UserPost> userPosts = getUserPostsByPost(post);

        return DetailedPostResponse.builder()
            .longitude(post.getLongitude())
            .latitude(post.getLatitude())
            .store(post.getStore())
            .minPrice(post.getMinPrice())
            .deliveryCost(post.getDeliveryCost())
            .menus(getNickMenus(userPosts))
            .sumPrice(getSumPrice(userPosts, post))
            .deadline(getDeadline(getDeadline(post)))
            .status(post.getPostStatus())
            .build();
    }

    @Override
    public DetailedPostResponse getPost(Long postId, User user) {
        Post post = getPostById(postId);
        List<UserPost> userPosts = getUserPostsByPost(post);
        User host = getAuthor(userPosts);

        return DetailedPostResponse.builder()
            .id(post.getId())
            .hostId(host.getId())
            .hostNick(host.getNickname())
            .longitude(post.getLongitude())
            .latitude(post.getLatitude())
            .store(post.getStore())
            .minPrice(post.getMinPrice())
            .deliveryCost(post.getDeliveryCost())
            .menus(getNickMenus(userPosts))
            .sumPrice(getSumPrice(userPosts, post))
            .deadline(getDeadline(getDeadline(post)))
            .status(post.getPostStatus())
            .role(getRoleByUserAndUserPosts(user, userPosts))
            .build();
    }

    @Override
    public List<BriefPostResponse> getPostsByCategoryForAnyone(int page, String category) {
        List<Post> posts;

        if (category.equals(CategoryEnum.ALL.toString())) {
            posts = findPage(page);

        } else if (checkIfCategoryEnum(category)) {
            CategoryEnum categoryEnum = CategoryEnum.valueOf(category);
            Pageable pageable = PageRequest.of(page, pagesize,
                Sort.by("deadline").descending());

            posts = postRepository.findAllByCategoryEquals(pageable, categoryEnum)
                .getContent();

        } else {
            Pageable pageable = PageRequest.of(page, pagesize,
                Sort.by("deadline").descending());

            posts = postRepository.findAllByCuisineEquals(pageable, category)
                .getContent();
        }
        return postsToBriefResponses(posts);
    }

    @Override
    public List<BriefPostResponse> getPostsByCategory(int page, String category, User user) {
        List<Post> posts;

        if (category.equals(CategoryEnum.ALL.toString())) {
            return getAllPosts(page, user);
        } else if (checkIfCategoryEnum(category)) {
            CategoryEnum categoryEnum = CategoryEnum.valueOf(category);
            posts = postCustomRepository.getPostsByDistanceAndCategory(page, user, categoryEnum);
        } else {
            posts = postCustomRepository.getPostsByCuisine(page, user, category);
        }
        return postsToBriefResponses(posts);
    }

    @Override
    public List<BriefPostResponse> getStatusPostsByCategory(int page, String category,
        String status, User user) {
        PostStatusEnum statusEnum = PostStatusEnum.valueOf(status);

        if (category.equals(CategoryEnum.ALL.toString())) {
            return getAllStatusPosts(page, statusEnum, user);

        } else if (checkIfCategoryEnum(category)) {
            CategoryEnum categoryEnum = CategoryEnum.valueOf(category);
            List<Post> posts = postCustomRepository.getPostsByStatusAndCategoryOrderByDistance(page,
                statusEnum, categoryEnum, user);
            return postsToBriefResponses(posts);

        } else {
            List<Post> posts = postCustomRepository.getPostsByStatusAndCuisine(page,
                statusEnum, category, user);
            return postsToBriefResponses(posts);
        }
    }

    @Override
    public List<BriefPostResponse> searchPostForAnyone(int page, String keyword) {
        Pageable pageWithTenPosts = PageRequest.of(page, pagesize, Sort.by("deadline").descending());
        List<Post> posts = postRepository.findPostByStoreContaining(pageWithTenPosts, keyword)
            .getContent();
        return postsToBriefResponses(posts);
    }

    @Override
    public List<BriefPostResponse> searchPost(int page, String keyword, User user) {
        List<Post> posts;

        if (user.getLatitude() == null || user.getLongitude() == null) {
            Pageable pageable = PageRequest.of(page, pagesize, Sort.by("deadline").descending());
            posts = postRepository.findPostByStoreContaining(pageable, keyword).getContent();
        } else {
            posts = postCustomRepository.getPostsByDistanceAndKeyword(page, user, keyword);
        }
        return postsToBriefResponses(posts);
    }

    @Override
    public List<BriefPostResponse> searchStatusPost(int page, String keyword, String status,
        User user) {
        PostStatusEnum statusEnum = PostStatusEnum.valueOf(status);

        List<Post> posts = postCustomRepository.getPostsByStatusAndKeywordOrderByDistance(page,
            statusEnum, keyword, user);
        return postsToBriefResponses(posts);

    }

    private List<Post> findPage(int page) {
        Pageable pageWithTenPosts = PageRequest.of(page, pagesize, Sort.by("deadline").descending());
        Page<Post> postPage = postRepository.findAll(pageWithTenPosts);
        List<Post> posts = postPage.getContent();
        return posts;
    }

    private List<BriefPostResponse> postsToBriefResponses(List<Post> posts) {
        return posts.stream()
            .map((Post post) ->
                BriefPostResponse
                    .builder()
                    .id(post.getId())
            .author(getAuthorNick(getUserPostsByPost(post)))
                    .store(post.getStore())
                    .deadline(getDeadline(getDeadline(post)))
                    .minPrice(post.getMinPrice())
                    .sumPrice(getSumPrice(getUserPostsByPost(post), post))
                    .status(post.getPostStatus())
            .build())
            .toList();
    }

    private User getAuthor(List<UserPost> userPosts) {
        for (UserPost userpost : userPosts) {
            if (userpost.getRole().equals(UserPostRole.HOST)) {
                return userpost.getUser();
            }
        }
        throw new GlobalException(UserPostErrorCode.NOT_FOUND_HOST);
    }

    private String getAuthorNick(List<UserPost> userPosts) {
        for (UserPost userpost : userPosts) {
            if (userpost.getRole().equals(UserPostRole.HOST)) {
                return userpost.getUser().getNickname();
            }
        }
        return " ";
    }

    private int getSumPrice(List<UserPost> userposts, Post post) {
        List<Menu> menus = menuRepository.findAllByPost(post);
        return getSumPrice(userposts, menus);
    }

    private int getSumPrice(List<UserPost> userposts, List<Menu> menus) {
        int sumPrice = 0;

        //add all prices from the menus from the users who are participating/hosting a post
        for (Menu menu : menus) {
            for (UserPost userpost : userposts) {
                if (userpost.getUser().getId().equals(menu.getUser().getId())) {
                    sumPrice += menu.getPrice();
                    break;
                }
            }
        }

        return sumPrice;
    }

    private LocalDateTime getDeadline(Post post) {
        //remove nano sencods from the LocalDateTime
        return post.getDeadline().withNano(0);
    }

    private String getDeadline(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        int days = deadline.getDayOfYear() - now.getDayOfYear();
        int hours = deadline.getHour() - now.getHour();
        int mins = deadline.getMinute() - now.getMinute();
        if (mins < 0) {
            hours--;
            mins = 60 + mins;
        }
        if (hours < 0) {
            hours = 0;
        }
        return days + "일 " + hours + "시 " + mins + " 분";
    }

    private Post getPostById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(PostErrorCode.NOT_FOUND_POST));
        return post;
    }

    private List<UserPost> getUserPostsByPost(Post post) {
        return userPostRepository.findAllByPost(post);
    }

    private List<NickMenusResponse> getNickMenus(List<UserPost> userposts) {
        List<NickMenusResponse> menus =
            //List<UserPost> -> List<NickMenusResponse>
            userposts.stream()
                .map((UserPost userpost) -> new NickMenusResponse(userpost.getUser().getNickname(),
                    //List<Menu> menus -> List<MenuResponse>
                    getUserMenus(userpost.getUser(), userpost.getPost()).stream().map(
                        (Menu menu) -> new MenuResponse(menu.getId(), menu.getMenuname(),
                            menu.getPrice())).toList())).toList();
        return menus;
    }

    private List<Menu> getUserMenus(User user, Post post) {
        return menuRepository.findAllByUserAndPost(user, post);
    }

    private UserPostRole getRoleByUserAndUserPosts(User user, List<UserPost> userPosts) {
        for (UserPost userPost : userPosts) {
            if (userPost.getUser().getId().equals(user.getId())) {
                return userPost.getRole();
            }
        }
        return null;
    }

    private List<BriefPostResponse> getAllPosts(int page, User user) {
        List<Post> posts;

        if (user.getLatitude() == null || user.getLongitude() == null) {
            posts = findPage(page);
        } else {
            posts = postCustomRepository.getPostsByDistance(page, user);
        }
        return postsToBriefResponses(posts);
    }

    private List<BriefPostResponse> getAllStatusPosts(int page, PostStatusEnum status, User user) {
        List<Post> posts = postCustomRepository.getPostsByStatusOrderByDistance(page, status, user);
        return postsToBriefResponses(posts);
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

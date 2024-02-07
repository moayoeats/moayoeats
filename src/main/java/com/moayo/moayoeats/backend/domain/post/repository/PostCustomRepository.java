package com.moayo.moayoeats.backend.domain.post.repository;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface PostCustomRepository {

    List<Post> getPostsByDistance(int page,User user);

    List<Post> getPostsByStatus(int page, PostStatusEnum status, User user);

    List<Post> getPostsByCategory(int page, User user, CategoryEnum category);

    List<Post> getPostsByKeyword(int page, User user, String keyword);

    List<Post> getPostsByStatusAndCategory(int page, PostStatusEnum status, CategoryEnum category ,User user);

    List<Post> getPostsByStatusAndKeyword(int page, PostStatusEnum statusEnum, String keyword , User user);

    List<Post> getPostsByCuisine(int page, User user, String cuisine);

    List<Post> getPostsByStatusAndCuisine(int page, PostStatusEnum statusEnum, String cuisine, User user);

}

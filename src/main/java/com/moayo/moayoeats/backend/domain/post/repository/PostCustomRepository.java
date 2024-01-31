package com.moayo.moayoeats.backend.domain.post.repository;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface PostCustomRepository {

    List<Post> getPostsByDistance(int page,User user);
    List<Post> getPostsByDistanceAndCategory(int page, User user, CategoryEnum category);

    List<Post> getPostsByDistanceAndKeyword(int page, User user, String keyword);

}

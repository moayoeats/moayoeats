package com.moayo.moayoeats.backend.domain.post.repository;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.QPost;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostsByDistance(int page, User user) {

        QPost post = QPost.post;
        int pagesize = 5;
        int offset = page*pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .orderBy(((post.latitude.subtract(user.getLatitude())).multiply(
                    (post.latitude.subtract(user.getLatitude())))
                .add((post.longitude.subtract(user.getLongitude())).multiply(
                    (post.longitude.subtract(user.getLongitude())))))
                .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

    @Override
    public List<Post> getPostsByDistanceAndCategory(int page, User user, CategoryEnum category) {

        QPost post = QPost.post;
        int pagesize = 5;
        int offset = page*pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.category.eq(category))
            .orderBy(((post.latitude.subtract(user.getLatitude())).multiply(
                    (post.latitude.subtract(user.getLatitude())))
                .add((post.longitude.subtract(user.getLongitude())).multiply(
                    (post.longitude.subtract(user.getLongitude())))))
                .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

    @Override
    public List<Post> getPostsByDistanceAndKeyword(int page, User user, String keyword) {
        QPost post = QPost.post;
        int pagesize = 5;
        int offset = page*pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.store.contains(keyword))
            .orderBy(((post.latitude.subtract(user.getLatitude())).multiply(
                    (post.latitude.subtract(user.getLatitude())))
                .add((post.longitude.subtract(user.getLongitude())).multiply(
                    (post.longitude.subtract(user.getLongitude())))))
                .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }
}

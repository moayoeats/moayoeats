package com.moayo.moayoeats.backend.domain.post.repository;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
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
    private final int pagesize = 5;

    @Override
    public List<Post> getPostsByDistance(int page, User user) {

        QPost post = QPost.post;
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
    public List<Post> getPostsByStatusOrderByDistance(int page, PostStatusEnum status, User user) {
        if(user.getLongitude()==null||user.getLatitude()==null){
            return getPostsByStatus(page, status);
        }else{
            return getPostsByStatus(page, status, user);
        }
    }

    @Override
    public List<Post> getPostsByDistanceAndCategory(int page, User user, CategoryEnum category) {

        QPost post = QPost.post;
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

    private List<Post> getPostsByStatus(int page, PostStatusEnum status){
        QPost post = QPost.post;
        int offset = page*pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.postStatus.eq(status))
            .orderBy(post.deadline
                .desc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

    private List<Post> getPostsByStatus(int page, PostStatusEnum status,User user){
        QPost post = QPost.post;
        int offset = page*pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.postStatus.eq(status))
            .orderBy(((post.latitude.subtract(user.getLatitude())).multiply(
                    (post.latitude.subtract(user.getLatitude())))
                .add((post.longitude.subtract(user.getLongitude())).multiply(
                    (post.longitude.subtract(user.getLongitude())))))
                .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

}

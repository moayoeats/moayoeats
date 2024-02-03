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
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory.selectFrom(post)
            .orderBy(((post.latitude.subtract(user.getLatitude()))
                .multiply((post.latitude.subtract(user.getLatitude())))
                .add((post.longitude.subtract(user.getLongitude()))
                    .multiply((post.longitude.subtract(user.getLongitude())))))
                .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

    @Override
    public List<Post> getPostsByStatusOrderByDistance(int page, PostStatusEnum status, User user) {
        if (user.getLongitude() == null || user.getLatitude() == null) {
            return getPostsByStatus(page, status);
        } else {
            return getPostsByStatus(page, status, user);
        }
    }

    @Override
    public List<Post> getPostsByDistanceAndCategory(int page, User user, CategoryEnum category) {

        List<Post> posts;
        if(user.getLongitude()==null||user.getLatitude()==null){
            posts = getPostsByCategory(page, category);
        }else{
            posts = getPostsByCategory(page, category, user);
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByDistanceAndKeyword(int page, User user, String keyword) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.store.contains(keyword))
            .orderBy(((post.latitude.subtract(user.getLatitude()))
                    .multiply((post.latitude.subtract(user.getLatitude())))
                    .add((post.longitude.subtract(user.getLongitude()))
                        .multiply((post.longitude.subtract(user.getLongitude())))))
                    .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

    @Override
    public List<Post> getPostsByStatusAndCategoryOrderByDistance(int page, PostStatusEnum status,
        CategoryEnum category, User user) {

        if (user.getLongitude() == null || user.getLatitude() == null) {
            return getPostsByStatusAndCategory(page, status, category);
        } else {
            return getPostsByStatusAndCategory(page, status, category, user);
        }
    }

    @Override
    public List<Post> getPostsByStatusAndKeywordOrderByDistance(int page, PostStatusEnum statusEnum,
        String keyword, User user) {
        if(user.getLatitude()==null||user.getLongitude()==null){
            return getPostsByStatusAndKeyword(page, statusEnum, keyword);
        }else{
            return getPostsByStatusAndKeyword(page,statusEnum,keyword,user);
        }
    }

    private List<Post> getPostsByStatus(int page, PostStatusEnum status) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.postStatus.eq(status))
            .orderBy(post.deadline.desc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

    private List<Post> getPostsByStatus(int page, PostStatusEnum status, User user) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.postStatus.eq(status))
            .orderBy(((post.latitude.subtract(user.getLatitude()))
                .multiply((post.latitude.subtract(user.getLatitude())))
                .add((post.longitude.subtract(user.getLongitude()))
                    .multiply((post.longitude.subtract(user.getLongitude())))))
                .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

    private List<Post> getPostsByStatusAndCategory(int page, PostStatusEnum status,
        CategoryEnum category) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.postStatus.eq(status)
                .and(post.category.eq(category)))
            .orderBy(post.deadline.desc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

    private List<Post> getPostsByStatusAndCategory(int page, PostStatusEnum status,
        CategoryEnum category, User user) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(
                post.postStatus.eq(status)
                    .and(post.category.eq(category)))
            .orderBy(((post.latitude.subtract(user.getLatitude()))
                .multiply((post.latitude.subtract(user.getLatitude())))
                .add((post.longitude.subtract(user.getLongitude()))
                    .multiply((post.longitude.subtract(user.getLongitude())))))
                .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

    private List<Post> getPostsByCategory(int page, CategoryEnum category, User user){
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory.selectFrom(post)
            .where(post.category.eq(category))
            .orderBy(
                ((post.latitude.subtract(user.getLatitude()))
                    .multiply((post.latitude.subtract(user.getLatitude())))
                    .add((post.longitude.subtract(user.getLongitude()))
                        .multiply((post.longitude.subtract(user.getLongitude())))))
                    .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

    private List<Post> getPostsByCategory(int page, CategoryEnum category){
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory.selectFrom(post)
            .where(post.category.eq(category))
            .orderBy(post.deadline.desc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

    private List<Post> getPostsByStatusAndKeyword(int page, PostStatusEnum statusEnum, String keyword){
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.store.contains(keyword).and(post.postStatus.eq(statusEnum)))
            .orderBy(post.deadline.desc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

    private List<Post> getPostsByStatusAndKeyword(int page, PostStatusEnum statusEnum, String keyword, User user){
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.store.contains(keyword).and(post.postStatus.eq(statusEnum)))
            .orderBy(((post.latitude.subtract(user.getLatitude()))
                .multiply((post.latitude.subtract(user.getLatitude())))
                .add((post.longitude.subtract(user.getLongitude()))
                    .multiply((post.longitude.subtract(user.getLongitude())))))
                .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

}

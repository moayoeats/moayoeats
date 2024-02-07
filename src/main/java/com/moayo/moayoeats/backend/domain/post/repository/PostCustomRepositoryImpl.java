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
    public List<Post> getPostsByStatus(int page, PostStatusEnum status, User user) {
        if (user.getLongitude() == null || user.getLatitude() == null) {
            return getPostsByStatusWithoutAddress(page, status);
        } else {
            return getPostsByStatusWithAddress(page, status, user);
        }
    }

    @Override
    public List<Post> getPostsByCategory(int page, User user, CategoryEnum category) {

        List<Post> posts;
        if (user.getLongitude() == null || user.getLatitude() == null) {
            posts = getPostsByCategoryWithoutAddress(page, category);
        } else {
            posts = getPostsByCategoryWithAddress(page, category, user);
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByKeyword(int page, User user, String keyword) {

        List<Post> posts;
        if (user.getLongitude() == null || user.getLatitude() == null) {
            posts = getPostsByKeywordWithoutAddress(page, keyword);
        } else {
            posts = getPostsByKeywordWithAddress(page, keyword, user);
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByStatusAndCategory(int page, PostStatusEnum status,
        CategoryEnum category, User user) {

        if (user.getLongitude() == null || user.getLatitude() == null) {
            return getPostsByStatusAndCategoryWithoutAddress(page, status, category);
        } else {
            return getPostsByStatusAndCategoryWithAddress(page, status, category, user);
        }
    }

    @Override
    public List<Post> getPostsByStatusAndKeyword(int page, PostStatusEnum statusEnum,
        String keyword, User user) {
        if (user.getLatitude() == null || user.getLongitude() == null) {
            return getPostsByStatusAndKeywordWithoutAddress(page, statusEnum, keyword);
        } else {
            return getPostsByStatusAndKeywordWithAddress(page, statusEnum, keyword, user);
        }
    }

    @Override
    public List<Post> getPostsByCuisine(int page, User user, String cuisine) {
        if (user.getLongitude() == null || user.getLatitude() == null) {
            return getPostsByCuisineWithoutAddress(page, cuisine);
        } else {
            return getPostsByCuisineWithAddress(page, cuisine, user);
        }
    }

    @Override
    public List<Post> getPostsByStatusAndCuisine(int page, PostStatusEnum statusEnum,
        String cuisine, User user) {
        if (user.getLongitude() == null || user.getLatitude() == null) {
            return getPostsByStatusAndCuisineWithoutAddress(page, statusEnum, cuisine);
        } else {
            return getPostsByStatusAndCuisineWithAddress(page, statusEnum, cuisine, user);
        }
    }

    private List<Post> getPostsByStatusWithoutAddress(int page, PostStatusEnum status) {
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

    private List<Post> getPostsByStatusWithAddress(int page, PostStatusEnum status, User user) {
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

    private List<Post> getPostsByStatusAndCategoryWithoutAddress(int page, PostStatusEnum status,
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

    private List<Post> getPostsByStatusAndCategoryWithAddress(int page, PostStatusEnum status,
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

    private List<Post> getPostsByCategoryWithoutAddress(int page, CategoryEnum category) {
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

    private List<Post> getPostsByCategoryWithAddress(int page, CategoryEnum category, User user) {
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

    private List<Post> getPostsByStatusAndKeywordWithoutAddress(int page, PostStatusEnum statusEnum,
        String keyword) {
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

    private List<Post> getPostsByStatusAndKeywordWithAddress(int page, PostStatusEnum statusEnum,
        String keyword, User user) {
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

    private List<Post> getPostsByCuisineWithoutAddress(int page, String cuisine) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory.selectFrom(post)
            .where(post.cuisine.eq(cuisine))
            .orderBy(post.deadline.desc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }

    private List<Post> getPostsByCuisineWithAddress(int page, String cuisine, User user) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory.selectFrom(post)
            .where(post.cuisine.eq(cuisine))
            .orderBy(
                ((post.latitude.subtract(user.getLatitude()))
                    .multiply((post.latitude.subtract(user.getLatitude())))
                    .add((post.longitude.subtract(user.getLongitude()))
                        .multiply((post.longitude.subtract(user.getLongitude())))))
                    .asc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

    private List<Post> getPostsByStatusAndCuisineWithoutAddress(int page, PostStatusEnum status,
        String cuisine) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.postStatus.eq(status)
                .and(post.cuisine.eq(cuisine)))
            .orderBy(post.deadline.desc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

    private List<Post> getPostsByStatusAndCuisineWithAddress(int page, PostStatusEnum status,
        String cuisine, User user) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.postStatus.eq(status)
                .and(post.cuisine.eq(cuisine)))
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

    private List<Post> getPostsByKeywordWithoutAddress(int page, String keyword) {
        QPost post = QPost.post;
        int offset = page * pagesize;

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .where(post.store.contains(keyword))
            .orderBy(post.deadline.desc())
            .offset(offset)
            .limit(pagesize)
            .fetchResults();

        return results.getResults();
    }

    private List<Post> getPostsByKeywordWithAddress(int page, String keyword, User user) {

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

        return results.getResults();
    }

}

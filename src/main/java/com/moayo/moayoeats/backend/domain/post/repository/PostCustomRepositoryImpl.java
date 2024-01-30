package com.moayo.moayoeats.backend.domain.post.repository;

import static com.querydsl.core.types.dsl.MathExpressions.power;

import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.QPost;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostsByDistance(int page, User viewer) {

        QPost post = QPost.post;

        Pageable pageable = PageRequest.of(page, 5);

        QueryResults<Post> results = jpaQueryFactory
            .selectFrom(post)
            .orderBy((post.latitude.subtract(viewer.getLatitude()))
                .add(post.longitude.subtract(viewer.getLongitude()))
                .desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<Post> closestPosts = results.getResults();
        return closestPosts;
    }
}

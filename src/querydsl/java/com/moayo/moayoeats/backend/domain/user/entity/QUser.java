package com.moayo.moayoeats.backend.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1152167743L;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath nickname = createString("nickname");

    public final ListPath<com.moayo.moayoeats.backend.domain.offer.entity.Offer, com.moayo.moayoeats.backend.domain.offer.entity.QOffer> offers = this.<com.moayo.moayoeats.backend.domain.offer.entity.Offer, com.moayo.moayoeats.backend.domain.offer.entity.QOffer>createList("offers", com.moayo.moayoeats.backend.domain.offer.entity.Offer.class, com.moayo.moayoeats.backend.domain.offer.entity.QOffer.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final ListPath<com.moayo.moayoeats.backend.domain.review.entity.Review, com.moayo.moayoeats.backend.domain.review.entity.QReview> reviews = this.<com.moayo.moayoeats.backend.domain.review.entity.Review, com.moayo.moayoeats.backend.domain.review.entity.QReview>createList("reviews", com.moayo.moayoeats.backend.domain.review.entity.Review.class, com.moayo.moayoeats.backend.domain.review.entity.QReview.class, PathInits.DIRECT2);

    public final ListPath<com.moayo.moayoeats.backend.domain.userpost.entity.UserPost, com.moayo.moayoeats.backend.domain.userpost.entity.QUserPost> userPosts = this.<com.moayo.moayoeats.backend.domain.userpost.entity.UserPost, com.moayo.moayoeats.backend.domain.userpost.entity.QUserPost>createList("userPosts", com.moayo.moayoeats.backend.domain.userpost.entity.UserPost.class, com.moayo.moayoeats.backend.domain.userpost.entity.QUserPost.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}


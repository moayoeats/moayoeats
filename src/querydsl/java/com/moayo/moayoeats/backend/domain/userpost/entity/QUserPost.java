package com.moayo.moayoeats.backend.domain.userpost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPost is a Querydsl query type for UserPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserPost extends EntityPathBase<UserPost> {

    private static final long serialVersionUID = -843204193L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserPost userPost = new QUserPost("userPost");

    public final com.moayo.moayoeats.backend.domain.post.entity.QPost post;

    public final EnumPath<UserPostRole> role = createEnum("role", UserPostRole.class);

    public final com.moayo.moayoeats.backend.domain.user.entity.QUser user;

    public QUserPost(String variable) {
        this(UserPost.class, forVariable(variable), INITS);
    }

    public QUserPost(Path<? extends UserPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserPost(PathMetadata metadata, PathInits inits) {
        this(UserPost.class, metadata, inits);
    }

    public QUserPost(Class<? extends UserPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.moayo.moayoeats.backend.domain.post.entity.QPost(forProperty("post")) : null;
        this.user = inits.isInitialized("user") ? new com.moayo.moayoeats.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}


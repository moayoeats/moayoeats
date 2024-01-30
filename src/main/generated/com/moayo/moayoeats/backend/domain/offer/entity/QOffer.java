package com.moayo.moayoeats.backend.domain.offer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOffer is a Querydsl query type for Offer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOffer extends EntityPathBase<Offer> {

    private static final long serialVersionUID = -1148943985L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOffer offer = new QOffer("offer");

    public final com.moayo.moayoeats.backend.global.entity.QBaseTime _super = new com.moayo.moayoeats.backend.global.entity.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.moayo.moayoeats.backend.domain.post.entity.QPost post;

    public final com.moayo.moayoeats.backend.domain.user.entity.QUser user;

    public QOffer(String variable) {
        this(Offer.class, forVariable(variable), INITS);
    }

    public QOffer(Path<? extends Offer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOffer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOffer(PathMetadata metadata, PathInits inits) {
        this(Offer.class, metadata, inits);
    }

    public QOffer(Class<? extends Offer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.moayo.moayoeats.backend.domain.post.entity.QPost(forProperty("post")) : null;
        this.user = inits.isInitialized("user") ? new com.moayo.moayoeats.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}


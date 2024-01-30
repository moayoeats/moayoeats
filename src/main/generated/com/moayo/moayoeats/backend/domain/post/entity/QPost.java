package com.moayo.moayoeats.backend.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1400213143L;

    public static final QPost post = new QPost("post");

    public final com.moayo.moayoeats.backend.global.entity.QBaseTime _super = new com.moayo.moayoeats.backend.global.entity.QBaseTime(this);

    public final BooleanPath amountIsSatisfied = createBoolean("amountIsSatisfied");

    public final EnumPath<CategoryEnum> category = createEnum("category", CategoryEnum.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deadline = createDateTime("deadline", java.time.LocalDateTime.class);

    public final NumberPath<Integer> deliveryCost = createNumber("deliveryCost", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final ListPath<com.moayo.moayoeats.backend.domain.menu.entity.Menu, com.moayo.moayoeats.backend.domain.menu.entity.QMenu> menus = this.<com.moayo.moayoeats.backend.domain.menu.entity.Menu, com.moayo.moayoeats.backend.domain.menu.entity.QMenu>createList("menus", com.moayo.moayoeats.backend.domain.menu.entity.Menu.class, com.moayo.moayoeats.backend.domain.menu.entity.QMenu.class, PathInits.DIRECT2);

    public final NumberPath<Integer> minPrice = createNumber("minPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<com.moayo.moayoeats.backend.domain.offer.entity.Offer, com.moayo.moayoeats.backend.domain.offer.entity.QOffer> offers = this.<com.moayo.moayoeats.backend.domain.offer.entity.Offer, com.moayo.moayoeats.backend.domain.offer.entity.QOffer>createList("offers", com.moayo.moayoeats.backend.domain.offer.entity.Offer.class, com.moayo.moayoeats.backend.domain.offer.entity.QOffer.class, PathInits.DIRECT2);

    public final EnumPath<PostStatusEnum> postStatus = createEnum("postStatus", PostStatusEnum.class);

    public final StringPath store = createString("store");

    public final NumberPath<Long> sumPrice = createNumber("sumPrice", Long.class);

    public QPost(String variable) {
        super(Post.class, forVariable(variable));
    }

    public QPost(Path<? extends Post> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPost(PathMetadata metadata) {
        super(Post.class, metadata);
    }

}


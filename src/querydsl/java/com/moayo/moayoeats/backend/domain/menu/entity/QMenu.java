package com.moayo.moayoeats.backend.domain.menu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = 175064167L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenu menu = new QMenu("menu");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath menuname = createString("menuname");

    public final com.moayo.moayoeats.backend.domain.order.entity.QOrder order;

    public final com.moayo.moayoeats.backend.domain.post.entity.QPost post;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.moayo.moayoeats.backend.domain.user.entity.QUser user;

    public QMenu(String variable) {
        this(Menu.class, forVariable(variable), INITS);
    }

    public QMenu(Path<? extends Menu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenu(PathMetadata metadata, PathInits inits) {
        this(Menu.class, metadata, inits);
    }

    public QMenu(Class<? extends Menu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.moayo.moayoeats.backend.domain.order.entity.QOrder(forProperty("order"), inits.get("order")) : null;
        this.post = inits.isInitialized("post") ? new com.moayo.moayoeats.backend.domain.post.entity.QPost(forProperty("post")) : null;
        this.user = inits.isInitialized("user") ? new com.moayo.moayoeats.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}


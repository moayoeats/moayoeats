package com.moayo.moayoeats.backend.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -1067672881L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.moayo.moayoeats.backend.global.entity.QBaseTime _super = new com.moayo.moayoeats.backend.global.entity.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.moayo.moayoeats.backend.domain.menu.entity.Menu, com.moayo.moayoeats.backend.domain.menu.entity.QMenu> menus = this.<com.moayo.moayoeats.backend.domain.menu.entity.Menu, com.moayo.moayoeats.backend.domain.menu.entity.QMenu>createList("menus", com.moayo.moayoeats.backend.domain.menu.entity.Menu.class, com.moayo.moayoeats.backend.domain.menu.entity.QMenu.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.moayo.moayoeats.backend.domain.user.entity.QUser receiver;

    public final EnumPath<com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole> senderRole = createEnum("senderRole", com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole.class);

    public final StringPath store = createString("store");

    public final com.moayo.moayoeats.backend.domain.user.entity.QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new com.moayo.moayoeats.backend.domain.user.entity.QUser(forProperty("receiver")) : null;
        this.user = inits.isInitialized("user") ? new com.moayo.moayoeats.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}


package com.sixheroes.onedayherocore.event.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvents is a Querydsl query type for Events
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvents extends EntityPathBase<Events> {

    private static final long serialVersionUID = 1830084385L;

    public static final QEvents events = new QEvents("events");

    public final com.sixheroes.onedayherocore.global.QBaseEntity _super = new com.sixheroes.onedayherocore.global.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final SimplePath<Object> eventData = createSimple("eventData", Object.class);

    public final EnumPath<EventType> eventType = createEnum("eventType", EventType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath success = createBoolean("success");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QEvents(String variable) {
        super(Events.class, forVariable(variable));
    }

    public QEvents(Path<? extends Events> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvents(PathMetadata metadata) {
        super(Events.class, metadata);
    }

}


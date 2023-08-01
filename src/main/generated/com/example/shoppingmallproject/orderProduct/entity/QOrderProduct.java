package com.example.shoppingmallproject.orderProduct.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderProduct is a Querydsl query type for OrderProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderProduct extends EntityPathBase<OrderProduct> {

    private static final long serialVersionUID = -1516395167L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderProduct orderProduct = new QOrderProduct("orderProduct");

    public final com.example.shoppingmallproject.share.QTimeStamped _super = new com.example.shoppingmallproject.share.QTimeStamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.example.shoppingmallproject.order.entity.QOrder order;

    public final com.example.shoppingmallproject.product.entity.QProduct product;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Long> unitPrice = createNumber("unitPrice", Long.class);

    public QOrderProduct(String variable) {
        this(OrderProduct.class, forVariable(variable), INITS);
    }

    public QOrderProduct(Path<? extends OrderProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderProduct(PathMetadata metadata, PathInits inits) {
        this(OrderProduct.class, metadata, inits);
    }

    public QOrderProduct(Class<? extends OrderProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.example.shoppingmallproject.order.entity.QOrder(forProperty("order"), inits.get("order")) : null;
        this.product = inits.isInitialized("product") ? new com.example.shoppingmallproject.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}


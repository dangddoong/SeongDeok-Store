package com.example.shoppingmallproject.seller.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeller is a Querydsl query type for Seller
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeller extends EntityPathBase<Seller> {

    private static final long serialVersionUID = 318787037L;

    public static final QSeller seller = new QSeller("seller");

    public final com.example.shoppingmallproject.share.QTimeStamped _super = new com.example.shoppingmallproject.share.QTimeStamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final SetPath<com.example.shoppingmallproject.product.entity.Product, com.example.shoppingmallproject.product.entity.QProduct> products = this.<com.example.shoppingmallproject.product.entity.Product, com.example.shoppingmallproject.product.entity.QProduct>createSet("products", com.example.shoppingmallproject.product.entity.Product.class, com.example.shoppingmallproject.product.entity.QProduct.class, PathInits.DIRECT2);

    public QSeller(String variable) {
        super(Seller.class, forVariable(variable));
    }

    public QSeller(Path<? extends Seller> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeller(PathMetadata metadata) {
        super(Seller.class, metadata);
    }

}


package com.example.shoppingmallproject.product.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.shoppingmallproject.product.dto.QProductResponseDto is a Querydsl Projection type for ProductResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductResponseDto extends ConstructorExpression<ProductResponseDto> {

    private static final long serialVersionUID = -724855457L;

    public QProductResponseDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> detail, com.querydsl.core.types.Expression<Long> price, com.querydsl.core.types.Expression<Long> stock) {
        super(ProductResponseDto.class, new Class<?>[]{long.class, String.class, String.class, long.class, long.class}, id, name, detail, price, stock);
    }

}


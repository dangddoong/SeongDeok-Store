package com.example.shoppingmallproject.orderProduct.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.shoppingmallproject.orderProduct.dto.QOrderProductResponseDto is a Querydsl Projection type for OrderProductResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOrderProductResponseDto extends ConstructorExpression<OrderProductResponseDto> {

    private static final long serialVersionUID = -2118401917L;

    public QOrderProductResponseDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<Long> unitPrice, com.querydsl.core.types.Expression<Integer> quantity, com.querydsl.core.types.Expression<String> productName) {
        super(OrderProductResponseDto.class, new Class<?>[]{long.class, long.class, long.class, int.class, String.class}, id, productId, unitPrice, quantity, productName);
    }

}


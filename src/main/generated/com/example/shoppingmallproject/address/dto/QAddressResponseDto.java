package com.example.shoppingmallproject.address.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.shoppingmallproject.address.dto.QAddressResponseDto is a Querydsl Projection type for AddressResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAddressResponseDto extends ConstructorExpression<AddressResponseDto> {

    private static final long serialVersionUID = -2001224555L;

    public QAddressResponseDto(com.querydsl.core.types.Expression<String> zipcode, com.querydsl.core.types.Expression<String> address) {
        super(AddressResponseDto.class, new Class<?>[]{String.class, String.class}, zipcode, address);
    }

}


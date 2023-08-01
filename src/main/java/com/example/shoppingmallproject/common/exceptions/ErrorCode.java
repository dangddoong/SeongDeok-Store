package com.example.shoppingmallproject.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
    Custom Error Code
    400 Bad Request
    401 Unauthorized
    403 Forbidden
    404 Not Found
    409 Conflict
    */

    //ADMIN 관련 ErrorCode

    // 토큰 관련 ErrorCode
    INVALID_AUTH_ADMIN(HttpStatus.UNAUTHORIZED, "ADMIN 토큰이 일치하지 않습니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),

    // 유저 관련 ErrorCode
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "중복된 username 입니다"),
    NOT_MATCH_INFORMATION(HttpStatus.BAD_REQUEST, "회원정보가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

    // 상품 관련 ErrorCode
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),
    SHORTAGE_PRODUCT_STOCK(HttpStatus.CONFLICT, "상품의 재고가 부족합니다."),

    // 주문 관련 ErrorCode
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
    NOT_FOUND_ORDER_PRODUCT(HttpStatus.NOT_FOUND, "해당 상세주문을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}

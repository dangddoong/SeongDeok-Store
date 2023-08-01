package com.example.shoppingmallproject.product.dto;

import com.example.shoppingmallproject.product.entity.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String detail;
    private Long price;
    private Long stock;

    /**
     * 해당 어노테이션은 QueryDSL 용입니다.
     */
    @QueryProjection
    public ProductResponseDto(Long id, String name, String detail, Long price, Long stock) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.stock = stock;
    }

    private ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.detail = product.getDetail();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }

    public ProductResponseDto of(Product product){
        return new ProductResponseDto(product);
    }
}

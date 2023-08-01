package com.example.shoppingmallproject.product.entity;

import com.example.shoppingmallproject.common.exceptions.CustomException;
import com.example.shoppingmallproject.common.exceptions.ErrorCode;
import com.example.shoppingmallproject.product.dto.ProductRequestDto;
import com.example.shoppingmallproject.seller.entity.Seller;
import com.example.shoppingmallproject.share.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Product extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String detail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private Long stock;

    @Builder
    public Product(String name, String detail, Seller seller, Long price, Long stock) {
        this.name = name;
        this.seller = seller;
        this.detail = detail;
        this.price = price;
        this.stock = stock;
    }

    public void setProduct(ProductRequestDto dto){
        if (dto.getDetail() != null){
            this.detail = dto.getDetail();
        }
        if (dto.getPrice() != null){
            this.price = dto.getPrice();
        }
        if (dto.getName() != null){
            this.name = dto.getName();
        }
        if (dto.getStock() != null){
            this.stock = dto.getStock();
        }
    }
    public void reduceStock(Long quantity){
        if((stock - quantity) < 0) throw new CustomException(ErrorCode.SHORTAGE_PRODUCT_STOCK);
        stock -= quantity;
    }
}

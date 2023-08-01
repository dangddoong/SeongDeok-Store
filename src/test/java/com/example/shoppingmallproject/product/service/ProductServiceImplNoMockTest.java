package com.example.shoppingmallproject.product.service;

import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.product.repository.ProductRepository;
import com.example.shoppingmallproject.seller.entity.Seller;
import com.example.shoppingmallproject.seller.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
@SpringBootTest
public class ProductServiceImplNoMockTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    private Seller seller;
    private Product product;

    @BeforeEach
    void setUp(){
        seller = Seller.builder()
                .name("name")
                .email("email")
                .password("password")
                .phone("Phone")
                .build();
        sellerRepository.save(seller);

        product = Product.builder()
                .price(100L)
                .detail("detail")
                .stock(3L)
                .name("name")
                .seller(seller).
                build();
        productRepository.save(product);
    }
    @Test
    void queryTest(){
        Product product = productRepository.getProductWithSeller(1L);

        if (!Objects.equals(product.getSeller().getId(), seller.getId())){
            throw new IllegalArgumentException("다른 판매자의 상품은 수정할 수 없습니다.");
        }
        // 예상대로 하나의 쿼리만 발생
    }
}

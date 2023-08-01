package com.example.shoppingmallproject.product.repository.query;

import com.example.shoppingmallproject.cart.entity.QCart;
import com.example.shoppingmallproject.product.dto.ProductResponseDto;
import com.example.shoppingmallproject.product.dto.QProductResponseDto;
import com.example.shoppingmallproject.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.shoppingmallproject.product.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> getProductsByCarts(List<Long> cartIds) {
        return jpaQueryFactory.selectFrom(product)
                .innerJoin(QCart.cart)
                .on(product.eq(QCart.cart.product))
                .where(QCart.cart.id.in(cartIds))
                .fetch();
    }

    @Override
    public boolean existsBySellerAndName(String sellerName, String productName){
        return jpaQueryFactory.selectFrom(product)
                .where(product.seller.name.eq(sellerName), product.name.eq(productName))
                .fetchFirst() != null;
    }
    @Override
    public Product getProductWithSeller(Long productId){
        return jpaQueryFactory.select(product)
                .from(product)
                .innerJoin(product.seller)
                .where(product.id.eq(productId))
                .fetchFirst();
    }
    @Override
    public List<ProductResponseDto> getMyProducts(Long sellerId){
        return jpaQueryFactory.select(new QProductResponseDto(product.id, product.name, product.detail, product.price, product.stock))
                .from(product)
                .where(product.seller.id.eq(sellerId))
                .fetch();
    }
}

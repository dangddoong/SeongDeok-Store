package com.example.shoppingmallproject.cart.repository.query;

import com.example.shoppingmallproject.cart.entity.Cart;

import com.example.shoppingmallproject.product.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

import static com.example.shoppingmallproject.cart.entity.QCart.cart;


@Repository
@RequiredArgsConstructor
public class CartQueryRepositoryImpl implements CartQueryRepository {
    /**
     * 어노테이션 @RequiredArgsConstructor  활용해서 final 필드 생성자 주입 (QueryDSL 사용을 위함)
     */
    private final JPAQueryFactory queryFactory;

    /**
     *
     * @param userId 패러미터로 userId 가 들어오면, 해당 유저를 특정해서
     * @return 그 유저가 가진 Carts 를 리턴합니다.
     */
    @Override
    public List<Cart> findCartsByUserId(Long userId) {
        return queryFactory
                .selectFrom(cart) // static import 해준 상태입니다. 해당 cart 는 Q 객체에요!
                .where(cart.user.id.eq(userId))
                .innerJoin(QProduct.product).fetchJoin()
                .fetch();
    }

    // QueryDSL 예시를 주기 위해 만든 매서드

    @Override
    public Optional<Cart> findCartWithProductsByUserId(Long userId){
        return Optional.ofNullable(queryFactory
                .selectFrom(cart)
                .innerJoin(cart.product).fetchJoin()
                .where(cart.user.id.eq(userId))
                .fetchFirst());
    }
    @Override
    public boolean isProductAlreadyExist(Long userId, Long productId){
        return queryFactory.select(cart.product.id) // 카트에 존재하는 프로덕트의 아이디를
                .from(cart) // 카트 테이블에서 찾는다
                .where(cart.user.id.eq(userId), cart.product.id.eq(productId))
                // 카트의 유저 아이디와, 카트의 상품 아이디가 모두 맞는 녀석, 즉 유저가 이미 가지고 있는 상품을
                .fetchFirst() !=null;
    }
}

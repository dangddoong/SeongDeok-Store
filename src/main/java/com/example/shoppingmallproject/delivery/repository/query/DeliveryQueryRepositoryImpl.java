package com.example.shoppingmallproject.delivery.repository.query;

import com.example.shoppingmallproject.delivery.entity.Delivery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.shoppingmallproject.delivery.entity.QDelivery.delivery;

@Repository
@RequiredArgsConstructor
public class DeliveryQueryRepositoryImpl implements DeliveryQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Delivery> findDeliveryByUserId(Long userId) {
        return null;
//        queryFactory.selectFrom(delivery)
//            .where()
    }

}

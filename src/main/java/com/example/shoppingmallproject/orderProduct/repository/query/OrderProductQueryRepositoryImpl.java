package com.example.shoppingmallproject.orderProduct.repository.query;

import com.example.shoppingmallproject.orderProduct.dto.OrderProductResponseDto;
import com.example.shoppingmallproject.orderProduct.dto.QOrderProductResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.example.shoppingmallproject.orderProduct.entity.QOrderProduct.orderProduct;


@Repository
@RequiredArgsConstructor
public class OrderProductQueryRepositoryImpl implements OrderProductQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<OrderProductResponseDto> getOrderProductDtos(Long orderId){
        return jpaQueryFactory
                .select(new QOrderProductResponseDto(orderProduct.id, orderProduct.product.id,
                        orderProduct.unitPrice, orderProduct.quantity, orderProduct.product.name))
                .from(orderProduct)
                .innerJoin(orderProduct.product)
                .where(orderProduct.order.id.eq(orderId))
                .fetch();
    }
}

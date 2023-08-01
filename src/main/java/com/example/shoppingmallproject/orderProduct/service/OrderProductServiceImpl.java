package com.example.shoppingmallproject.orderProduct.service;

import com.example.shoppingmallproject.common.exceptions.CustomException;
import com.example.shoppingmallproject.common.exceptions.ErrorCode;
import com.example.shoppingmallproject.order.entity.Order;
import com.example.shoppingmallproject.orderProduct.dto.OrderProductResponseDto;
import com.example.shoppingmallproject.orderProduct.entity.OrderProduct;
import com.example.shoppingmallproject.orderProduct.repository.OrderProductRepository;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.seller.entity.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService{
    private final OrderProductRepository orderProductRepository;


    @Override
    @Transactional
    public void createOrderProduct(Order order, Product product, Long quantity, Long unitPrice) {
        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(quantity.intValue())
                .unitPrice(unitPrice).build();
        orderProductRepository.save(orderProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderProductResponseDto> getOrderProductDtos(Long orderId) {
        List<OrderProductResponseDto> orderProductDtos = orderProductRepository.getOrderProductDtos(orderId);
        if(orderProductDtos.isEmpty()) throw new CustomException(ErrorCode.NOT_FOUND_ORDER_PRODUCT);
        return orderProductDtos;
    }
}

package com.example.shoppingmallproject.orderProduct.service;

import com.example.shoppingmallproject.order.entity.Order;
import com.example.shoppingmallproject.orderProduct.dto.OrderProductResponseDto;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.seller.entity.Seller;

import java.util.List;

public interface OrderProductService {
    void createOrderProduct(Order order, Product product, Long quantity, Long totalPrice);

    List<OrderProductResponseDto> getOrderProductDtos(Long orderId);

}

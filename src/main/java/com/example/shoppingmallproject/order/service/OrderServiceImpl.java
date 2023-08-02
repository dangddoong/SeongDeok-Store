package com.example.shoppingmallproject.order.service;

import com.example.shoppingmallproject.common.exceptions.CustomException;
import com.example.shoppingmallproject.common.exceptions.ErrorCode;
import com.example.shoppingmallproject.order.dto.OrderDetailsDto;
import com.example.shoppingmallproject.order.dto.OrderRequestDto;
import com.example.shoppingmallproject.order.dto.OrderResponseDto;
import com.example.shoppingmallproject.order.entity.Order;
import com.example.shoppingmallproject.order.repository.OrderRepository;
import com.example.shoppingmallproject.orderProduct.dto.OrderProductResponseDto;
import com.example.shoppingmallproject.orderProduct.service.OrderProductService;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.product.repository.ProductRepository;
import com.example.shoppingmallproject.product.service.ProductService;
import com.example.shoppingmallproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderProductService orderProductService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Long createOrder(OrderRequestDto dto, User user) {
        List<Product> products = productService.getProductsByIds(dto.getProductIds());
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        reduceProductsStock(dto.getOrderDetailsDtos(), productMap);
        Order order = Order.builder()
                .user(user)
                .totalPrice(calculateTotalPrice(dto.getOrderDetailsDtos(), productMap))
                .build();
        orderRepository.save(order);
        createOrderProducts(dto.getOrderDetailsDtos(), productMap, order);
        return order.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
        List<OrderProductResponseDto> orderProductResponseDtos =orderProductService.getOrderProductDtos(orderId);
        return OrderResponseDto.of(order, orderProductResponseDtos);
    }

    private Long calculateTotalPrice(List<OrderDetailsDto> orderDetailsDtos, Map<Long, Product> productMap) {
        long totalPrice = 0L;
        for (OrderDetailsDto dto: orderDetailsDtos){
            Long price = productMap.get(dto.getProductId()).getPrice();
            totalPrice += dto.getQuantity() * price;
        }
        return totalPrice;
    }

    private void createOrderProducts(List<OrderDetailsDto> orderDetailsDtos, Map<Long, Product> productMap, Order order){
        for (OrderDetailsDto detailsDto: orderDetailsDtos){
            Product product = productMap.get(detailsDto.getProductId());
            orderProductService.createOrderProduct(order, product, detailsDto.getQuantity(), product.getPrice());
        }
    }


    private void reduceProductsStock(List<OrderDetailsDto> orderDetailsDtos, Map<Long, Product> productMap){
        for (OrderDetailsDto detailsDto: orderDetailsDtos){
            Product product = productMap.get(detailsDto.getProductId());
//            product.reduceStock(detailsDto.getQuantity());
            int i = productRepository.reduceStock(detailsDto.getQuantity(), product.getId());
            if ( i != 1){
                System.out.println("i is " + i);
                throw new RuntimeException();
            }
        }
    }
}

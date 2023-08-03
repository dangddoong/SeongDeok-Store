package com.example.shoppingmallproject.order;

import com.example.shoppingmallproject.order.dto.OrderDetailsDto;
import com.example.shoppingmallproject.order.dto.OrderRequestDto;
import com.example.shoppingmallproject.order.entity.Order;
import com.example.shoppingmallproject.order.repository.OrderRepository;
import com.example.shoppingmallproject.order.service.OrderService;
import com.example.shoppingmallproject.orderProduct.entity.OrderProduct;
import com.example.shoppingmallproject.orderProduct.repository.OrderProductRepository;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.product.repository.ProductRepository;
import com.example.shoppingmallproject.seller.entity.Seller;
import com.example.shoppingmallproject.seller.repository.SellerRepository;
import com.example.shoppingmallproject.user.entity.User;
import com.example.shoppingmallproject.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class OrderConcurrencyTestThree {
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private SellerRepository sellerRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private OrderProductRepository orderProductRepository;
  @Autowired
  private OrderService orderService;
  private User user;
  private OrderRequestDto orderRequestDto;
  private OrderRequestDto orderRequestDto2;
  @BeforeEach
  void setUp(){
    user = User.builder()
        .email("email@test.com")
        .password("encodingPassword")
        .username("username")
        .phone("010-1234-1234")
        .build();
    userRepository.save(user);
    Seller seller = Seller.builder()
        .name("name")
        .email("email")
        .password("password")
        .phone("Phone")
        .build();
    sellerRepository.save(seller);
    Long stock = 3002L;
    Product product1 = Product.builder()
        .price(100L)
        .detail("detail")
        .stock(stock)
        .name("name")
        .seller(seller).
        build();
    Product product2 = Product.builder()
        .price(100L)
        .detail("detail")
        .stock(stock)
        .name("name")
        .seller(seller).
        build();
    productRepository.save(product1);
    productRepository.save(product2);

    OrderDetailsDto detailsDto1 = new OrderDetailsDto(product1.getId(), 1L);
    OrderDetailsDto detailsDto2 = new OrderDetailsDto(product2.getId(), 1L);
    List<OrderDetailsDto> detailsDtos = new ArrayList<>();
    detailsDtos.add(detailsDto1);
    detailsDtos.add(detailsDto2);
    List<OrderDetailsDto> detailsDtos2 = new ArrayList<>();
    detailsDtos2.add(detailsDto2);
    orderRequestDto = new OrderRequestDto(detailsDtos);
    orderRequestDto2 = new OrderRequestDto(detailsDtos2);
  }

  @Test
//  @Transactional
//  @Rollback(false)
  void 재고차감_동시성_테스트() throws InterruptedException {
    int num = 1500;
    ExecutorService executorService = Executors.newFixedThreadPool(3000);
    CountDownLatch countDownLatch = new CountDownLatch(3000);

    for (int i = 0; i < num; i++) {
      executorService.submit(() -> {
        try {
          long beforeTime = System.currentTimeMillis();
          orderService.createOrder(orderRequestDto, user);
          System.out.println("p12: " + (System.currentTimeMillis() - beforeTime));
        } finally {
          countDownLatch.countDown();
        }
      });
    }
    for (int i = 0; i < num; i++) {
      executorService.submit(() -> {
        try {
          long beforeTime = System.currentTimeMillis();
          orderService.createOrder(orderRequestDto2, user);
          System.out.println("p2: " + (System.currentTimeMillis() - beforeTime));
        } finally {
          countDownLatch.countDown();
        }
      });
    }
    countDownLatch.await();
//    Assertions.assertEquals(0L, testProduct.getStock());
    Product testProduct1 = productRepository.findById(1L).orElseThrow();
    Product testProduct2 = productRepository.findById(2L).orElseThrow();
    List<OrderProduct> all = orderProductRepository.findAll();
    List<Order> all1 = orderRepository.findAll();
    System.out.println("-----------sout-----------");
    System.out.println("order 개수: " + all1.size());
    System.out.println("orderProduct 개수: " + all.size());
    System.out.println("product1 stock: " + testProduct1.getStock());
    System.out.println("product2 stock: " + testProduct2.getStock());
    orderService.createOrder(orderRequestDto, user);

  }
}
package com.example.shoppingmallproject.order;

import com.example.shoppingmallproject.order.dto.OrderDetailsDto;
import com.example.shoppingmallproject.order.dto.OrderRequestDto;
import com.example.shoppingmallproject.order.entity.Order;
import com.example.shoppingmallproject.order.repository.OrderRepository;
import com.example.shoppingmallproject.order.service.OrderFacade;
import com.example.shoppingmallproject.order.service.OrderService;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class OrderConcurrencyTest {
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private SellerRepository sellerRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private OrderFacade orderFacade;
  private User user;
  private Product product;
  private OrderRequestDto orderRequestDto;
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

    product = Product.builder()
        .price(100L)
        .detail("detail")
        .stock(20L)
        .name("name")
        .seller(seller).
        build();
    productRepository.save(product);

    OrderDetailsDto detailsDto = new OrderDetailsDto(product.getId(), 1L);
    List<OrderDetailsDto> detailsDtos = new ArrayList<>();
    detailsDtos.add(detailsDto);
    orderRequestDto = new OrderRequestDto(detailsDtos);
  }

  @Test
//  @Transactional
//  @Rollback(false)
  void 재고차감_동시성_테스트() throws InterruptedException {
    int num = 100;
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    CountDownLatch countDownLatch = new CountDownLatch(num);

    for (int i = 0; i < num; i++) {
      executorService.submit(() -> {
        try {
          orderFacade.createOrderWithDistributedLock(orderRequestDto, user);
        } finally {
          countDownLatch.countDown();
        }
      });
    }
    countDownLatch.await();
//    orderService.createOrder(orderRequestDto, user);
    Product testProduct = productRepository.findById(product.getId()).orElseThrow();
//    Assertions.assertEquals(0L, testProduct.getStock());
    List<Order> all = orderRepository.findAll();
    System.out.println(all.size());
    System.out.println(testProduct.getStock());
  }
}



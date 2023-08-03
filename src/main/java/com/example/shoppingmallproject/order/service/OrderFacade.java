package com.example.shoppingmallproject.order.service;

import com.example.shoppingmallproject.order.dto.OrderRequestDto;
import com.example.shoppingmallproject.user.entity.User;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {

  private final RedissonClient redissonClient;
  private final OrderService orderService;

  public Long createOrderWithDistributedLock(OrderRequestDto dto, User user) {
    List<Long> productIds = dto.getProductIds();
    List<RLock> locks = productIds.stream()
        .map(productId -> redissonClient.getLock("productId:" + productId))
        .toList();
    try {
      for (RLock lock : locks) {
        boolean available = lock.tryLock(11, 1, TimeUnit.SECONDS);
        if (!available)
          throw new IllegalArgumentException();
      }
      return orderService.createOrder(dto, user);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      locks.forEach(Lock::unlock);
    }
  }
}

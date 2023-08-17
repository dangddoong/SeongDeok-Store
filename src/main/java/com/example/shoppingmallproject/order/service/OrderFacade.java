package com.example.shoppingmallproject.order.service;

import com.example.shoppingmallproject.order.dto.OrderRequestDto;
import com.example.shoppingmallproject.user.entity.User;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
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
    List<RLock> locks = getRLocks(dto);
    try {
      tryRLocks(locks);
      return orderService.createOrder(dto, user);
    } catch (InterruptedException e) {
      throw new RuntimeException(e); // Custom Exception을 만들어서 던져주면 더 좋겠지유
    } finally {
      locks.forEach(Lock::unlock);
    }
  }

  private void tryRLocks(List<RLock> locks) throws InterruptedException {
    for (RLock lock : locks) {
      boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
      if (!available)
        throw new IllegalArgumentException();
    }
  }

  private List<RLock> getRLocks(OrderRequestDto dto) {
    List<Long> productIds = dto.getProductIds();
    return productIds.stream()
        .map(productId -> redissonClient.getLock("productId:" + productId))
        .toList();
  }
}

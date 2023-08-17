package com.example.shoppingmallproject.order.service;

import com.example.shoppingmallproject.common.redis.RedisDAO;
import com.example.shoppingmallproject.order.dto.OrderRequestDto;
import com.example.shoppingmallproject.user.entity.User;
import com.sun.jdi.request.DuplicateRequestException;
import java.time.Duration;
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
  private final RedisDAO redisDAO;

  public Long createOrderWithDistributedLock(OrderRequestDto dto, User user) {
    List<RLock> locks = getRLocks(dto);
    try {
      tryRLocks(locks);
      checkDuplicateRequest(user.getId(), "createOrder");
      return orderService.createOrder(dto, user);
    } catch (InterruptedException e) {
      throw new RuntimeException(e); // Custom Exception을 만들어서 던져주면 더 좋겠지유
    } finally {
      locks.forEach(Lock::unlock);
    }
  }

  // methodName + userId를 중복요청 검증 키값으로 사용하기 위해서, 두 값을 인자로 받아와서 합쳐주고 있습니다.
  // String duplicateCheckKey를 인자로 받는다면, 분명 누군가는 정책과 다른 key값을 만들어서 넣어줄 것이기에..
  private void checkDuplicateRequest(Long userId, String methodName) {
    String duplicateCheckKey = methodName + userId;
    boolean isUniqueRequest = redisDAO.setIfAbsent(duplicateCheckKey, "value", Duration.ofMillis(10 * 1000L));
    if (!isUniqueRequest) throw new DuplicateRequestException(duplicateCheckKey);
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

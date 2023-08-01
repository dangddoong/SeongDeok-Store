package com.example.shoppingmallproject.delivery.repository;

import com.example.shoppingmallproject.delivery.entity.Delivery;
import com.example.shoppingmallproject.delivery.repository.query.DeliveryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryQueryRepository {

}

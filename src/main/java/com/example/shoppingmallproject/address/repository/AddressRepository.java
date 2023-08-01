package com.example.shoppingmallproject.address.repository;

import com.example.shoppingmallproject.address.entity.Address;
import com.example.shoppingmallproject.address.repository.query.AddressQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> , AddressQueryRepository {
}

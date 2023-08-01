package com.example.shoppingmallproject.address.repository.query;

import com.example.shoppingmallproject.address.dto.AddressResponseDto;
import com.example.shoppingmallproject.address.dto.QAddressResponseDto;
import com.example.shoppingmallproject.address.entity.Address;
import com.example.shoppingmallproject.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.shoppingmallproject.address.entity.QAddress.address;

@Repository
@RequiredArgsConstructor
public class AddressQueryRepositoryImpl implements AddressQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<AddressResponseDto> getMyAddresses(User user){
        return jpaQueryFactory.select(new QAddressResponseDto(address.zipCode, address.userAddress))
                .from(address)
                .where(address.user.id.eq(user.getId()))
                .fetch();
    }

    @Override
    public boolean existsByUserId(Long userId, String addressArg){
        return jpaQueryFactory.selectFrom(address)
                .where(address.user.id.eq(userId) , address.userAddress.eq(addressArg))
                // 같으면 중복이니까 이게 맞지
                .fetchFirst() != null;
    }
    @Override
    public Optional<Address> findAddressByUserAndId(User user, Long addressId){
        return Optional.ofNullable(jpaQueryFactory.selectFrom(address)
                .where(address.user.id.eq(user.getId()), address.id.eq(addressId))
                .fetchFirst());
    }
}

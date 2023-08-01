package com.example.shoppingmallproject.common.security.userDetails.service;

import com.example.shoppingmallproject.common.security.userDetails.entity.SellerDetailsImpl;
import com.example.shoppingmallproject.seller.entity.Seller;
import com.example.shoppingmallproject.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerDetailsServiceImpl implements UserDetailsServiceAddGetType{

  private final SellerRepository sellerRepository;

  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Seller seller = sellerRepository.findByEmail(email)
        .orElseThrow(()-> new UsernameNotFoundException("판매자를 찾을 수 없습니다."));
    return new SellerDetailsImpl(seller, email, seller.getId());
  }

  public UserDetailsServiceType getServiceType() {
    return UserDetailsServiceType.SELLER;
  }
}

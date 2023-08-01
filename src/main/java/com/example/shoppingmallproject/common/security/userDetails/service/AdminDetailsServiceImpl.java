package com.example.shoppingmallproject.common.security.userDetails.service;

import com.example.shoppingmallproject.admin.entity.Admin;
import com.example.shoppingmallproject.admin.repository.AdminRepository;
import com.example.shoppingmallproject.common.security.userDetails.entity.AdminDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsServiceAddGetType {

  private final AdminRepository adminRepository;

  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Admin admin = adminRepository.findByEmail(email)
        .orElseThrow(()-> new UsernameNotFoundException("관리자를 찾을 수 없습니다."));
    return new AdminDetailsImpl(admin, email, admin.getId());
  }

  public UserDetailsServiceType getServiceType() {
    return UserDetailsServiceType.ADMIN;
  }
}

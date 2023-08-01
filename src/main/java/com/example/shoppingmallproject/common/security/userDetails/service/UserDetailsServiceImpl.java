package com.example.shoppingmallproject.common.security.userDetails.service;

import com.example.shoppingmallproject.common.security.userDetails.entity.UserDetailsImpl;
import com.example.shoppingmallproject.user.entity.User;
import com.example.shoppingmallproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsServiceAddGetType{

  private final UserRepository userRepository;

  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    return new UserDetailsImpl(user, email, user.getId());
  }

  public UserDetailsServiceType getServiceType() {
    return UserDetailsServiceType.USER;
  }
}

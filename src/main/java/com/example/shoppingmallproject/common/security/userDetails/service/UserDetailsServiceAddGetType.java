package com.example.shoppingmallproject.common.security.userDetails.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsServiceAddGetType extends UserDetailsService {

  @Override
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
  UserDetailsServiceType getServiceType();
}

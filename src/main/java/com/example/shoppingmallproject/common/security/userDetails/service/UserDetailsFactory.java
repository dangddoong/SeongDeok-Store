package com.example.shoppingmallproject.common.security.userDetails.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsFactory {

  private final HashMap<UserDetailsServiceType, UserDetailsServiceAddGetType> serviceMap = new HashMap<>();

  public UserDetailsFactory(List<UserDetailsServiceAddGetType> serviceList) {
    for(UserDetailsServiceAddGetType service : serviceList){
      serviceMap.put(service.getServiceType(), service);
    }
  }

  public UserDetails getUserDetails(String loginId,UserDetailsServiceType serviceType){
    UserDetailsServiceAddGetType userDetailsService = serviceMap.get(serviceType);
    return userDetailsService.loadUserByUsername(loginId);
  }

}

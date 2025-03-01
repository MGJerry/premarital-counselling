//package com.example.demo.security.services;
//
//import com.example.demo.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.demo.repository.UserRepository;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//  @Autowired
//  UserRepository userRepository;
//
//  @Override
//  @Transactional
//  public UserDetails loadUserByUsername(String fullName) throws UsernameNotFoundException {
//    User user = userRepository.findByFullName(fullName)
//        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with fullName: " + fullName));
//
//    return UserDetailsImpl.build(user);
//  }
//
//}

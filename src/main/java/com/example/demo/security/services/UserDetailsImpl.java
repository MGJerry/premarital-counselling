//package com.example.demo.security.services;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import com.example.demo.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//public class UserDetailsImpl implements UserDetails {
//  private static final long serialVersionUID = 1L;
//
//  private Long userId;
//
//  private String fullName;
//
//  private String email;
//
//  @JsonIgnore
//  private String password;
//
//  private Collection<? extends GrantedAuthority> authorities;
//
//  public UserDetailsImpl(Long id, String fullName, String email, String password,
//      Collection<? extends GrantedAuthority> authorities) {
//    this.userId = id;
//    this.fullName = fullName;
//    this.email = email;
//    this.password = password;
//    this.authorities = authorities;
//  }
//
//  public static UserDetailsImpl build(User user) {
//    List<GrantedAuthority> authorities = user.getRoles().stream()
//        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
//        .collect(Collectors.toList());
//
//    return new UserDetailsImpl(
//        user.getId(),
//        user.getUsername(),
//        user.getEmail(),
//        user.getPassword(),
//        authorities);
//  }
//
//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return authorities;
//  }
//
//  public Long getId() {
//    return userId;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  @Override
//  public String getPassword() {
//    return password;
//  }
//
//  @Override
//  public String getUsername() {
//    return fullName;
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return true;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return true;
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o)
//      return true;
//    if (o == null || getClass() != o.getClass())
//      return false;
//    UserDetailsImpl user = (UserDetailsImpl) o;
//    return Objects.equals(userId, user.userId);
//  }
//}

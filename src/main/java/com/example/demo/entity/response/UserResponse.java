package com.example.demo.entity.response;

import com.example.demo.entity.User;
import com.example.demo.enums.EGender;
import com.example.demo.enums.EStatus;
import com.example.demo.model.ERole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private EGender gender;
    private String country;
    private String address;
    private String phone;
    private LocalDate birthday;
    private ERole role;
    private EStatus eStatus;
    private String bio;
    
    public static UserResponse fromUser(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setGender(user.getGender());
        response.setCountry(user.getCountry());
        response.setAddress(user.getAddress());
        response.setPhone(user.getPhone());
        response.setBirthday(user.getBirthday());
        response.setRole(user.getRole());
        response.setEStatus(user.geteStatus());
        response.setBio(user.getBio());
        return response;
    }
} 
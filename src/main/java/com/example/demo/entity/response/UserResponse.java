package com.example.demo.entity.response;

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
    private String imgurl;
    private ERole role;
    private EStatus eStatus;
    private String bio;
} 
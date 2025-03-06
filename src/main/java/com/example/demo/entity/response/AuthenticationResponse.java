package com.example.demo.entity.response;

import com.example.demo.model.ERole;

public class AuthenticationResponse {
    public long id ;
    public String username;
    public String email;
    public String fullName;
    public String phone;
    public String token;
    public ERole role;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(long id, String username, String email, String fullName, String phone, String token, ERole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.token = token;
        this.role = role;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}



package com.example.demo.entity.request;

import com.example.demo.model.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterRequest {
    @NotBlank(message = "Fullname is required")
    public String fullName;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    public String password;
    @NotBlank(message = "Email is required")
    @Email(message = "The email is invalid")
    public String email;
    public ERole role;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String fullName, String password, String email, ERole role) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

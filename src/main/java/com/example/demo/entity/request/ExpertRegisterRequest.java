package com.example.demo.entity.request;

import jakarta.validation.constraints.*;
import java.util.List;

public class ExpertRegisterRequest {
    @NotBlank(message = "FullName is required")
    public String fullName;
    
    @NotBlank(message = "username is required")
    public String userName;
    
    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "(03|05|07|08|09)[0-9]{8}", message = "Invalid phone number format")
    public String phone;
    
    @NotBlank(message = "email is required")
    @Email
    public String email;
    
    @NotBlank(message = "password is required")
    public String password;
    
    // Changed from specializationId to categoryIds to support multiple skills
    @NotNull(message = "At least one category is required")
    @Size(min = 1, message = "At least one category must be selected")
    public List<Long> categoryIds;
    
    @NotNull(message = "specialization level is required (1 - 3)")
    @Min(value = 1, message = "Specialization level must be at least 1")
    @Max(value = 3, message = "Specialization level must be at most 3")
    public int specializationLevel;

    // Add bio field
    public String bio;

    public ExpertRegisterRequest(String fullName, String userName, String phone, 
                                String email, String password, List<Long> categoryIds, int specializationLevel,
                                String bio) {
        this.fullName = fullName;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.categoryIds = categoryIds;
        this.specializationLevel = specializationLevel;
        this.bio = bio;
    }

    public ExpertRegisterRequest() {
        this.specializationLevel = specializationLevel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public int getSpecializationLevel() {
        return specializationLevel;
    }

    public void setSpecializationLevel(int specializationLevel) {
        this.specializationLevel = specializationLevel;
    }

    // Add getter and setter for bio
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

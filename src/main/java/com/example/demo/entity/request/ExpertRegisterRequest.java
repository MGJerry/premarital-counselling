package com.example.demo.entity.request;

import jakarta.validation.constraints.*;

public class ExpertRegisterRequest {
    @NotBlank(message = "FullName is required")
    public String fullName;
    @NotBlank(message = "username is required")
    public String userName;
    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "(03|05|07|08|09)[0-9]{8}", message = "Invalid phone number format")
    public String phone;
    @NotBlank(message = "profile picture is required")
    public String imgurl;
    @NotBlank(message = "email is required")
    @Email
    public String email;
    @NotBlank(message = "password is required")
    public String password;
    @NotBlank(message = "specialization is required")
    public String specialization;
    @NotNull(message = "specialization level is required (1 - 3)")
    @Min(value = 1, message = "Specialization level must be at least 1")
    @Max(value = 3, message = "Specialization level must be at most 3")
    public int specializationLevel;

    public ExpertRegisterRequest(String fullName, String userName, String phone, String imgurl, String email, String password, String specialization, int specializationLevel) {
        this.fullName = fullName;
        this.userName = userName;
        this.phone = phone;
        this.imgurl = imgurl;
        this.email = email;
        this.password = password;
        this.specialization = specialization;
        this.specializationLevel = specializationLevel;
    }

    public ExpertRegisterRequest() {
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getSpecializationLevel() {
        return specializationLevel;
    }

    public void setSpecializationLevel(int specializationLevel) {
        this.specializationLevel = specializationLevel;
    }
}

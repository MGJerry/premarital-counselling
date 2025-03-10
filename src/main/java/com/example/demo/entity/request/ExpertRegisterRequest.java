package com.example.demo.entity.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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

    public ExpertRegisterRequest(String fullName, String userName, String phone, String imgurl, String email, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.phone = phone;
        this.imgurl = imgurl;
        this.email = email;
        this.password = password;
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
}

package com.example.demo.entity.request;

public class ExpertRegisterRequest {
    public String fullName;
    public String userName;
    public String phone;
    public String imgurl;
    public String email;
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

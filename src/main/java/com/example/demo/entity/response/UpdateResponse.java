package com.example.demo.entity.response;

import java.time.LocalDate;

public class UpdateResponse {
    public String fullName;
    public String email;
    public String phone;
    public LocalDate birthday;
    public boolean Gender;
    public String imgurl;
    public String address;
    public String bio;

    public UpdateResponse() {
    }

    public UpdateResponse(String fullName, String email, String phone, LocalDate birthday, boolean gender, String imgurl, String address, String bio) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        Gender = gender;
        this.imgurl = imgurl;
        this.address = address;
        this.bio = bio;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

package com.example.demo.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class UpdateRequest {
    public String fullName;
    @Email(message = "Email is not invalid")
    public String email;
    @Pattern(regexp = "(03|05|07|08|09)[0-9]{8}", message = "Invalid phone number format")
    public String phone;
    @JsonFormat(pattern = "MM/dd/yyyy")
    public LocalDate birthday;
    public boolean Gender;
    public String imgurl;
    public String address;
    public String bio;

    public UpdateRequest() {
    }

    public UpdateRequest(String fullName, String email, String phone, LocalDate birthday, boolean gender, String imgurl, String address, String bio) {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}

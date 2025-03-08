package com.example.demo.entity;

import com.example.demo.enums.EStatus;
import com.example.demo.model.ERole;
import jakarta.persistence.Entity;

@Entity
public class Expert extends User{
    public String imgurl;
    public String language;

    public Expert(long id, String username, String email, String password, String fullName, String gender, String country, String phone, ERole role, EStatus eStatus, String imgurl, String language) {
        super(id, username, email, password, fullName, gender, country, phone, role, eStatus);
        this.imgurl = imgurl;
        this.language = language;
    }

    public Expert() {

    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

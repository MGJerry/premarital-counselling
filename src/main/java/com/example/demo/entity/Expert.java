package com.example.demo.entity;

import com.example.demo.enums.EGender;
import com.example.demo.enums.EStatus;
import com.example.demo.model.ERole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@DiscriminatorValue("EXPERT")
@Entity
public class Expert extends User{
    public String imgurl;
    public String language;

    public Expert(long id, String username, String email, String password, String fullName, EGender gender, String country, String phone, LocalDate birthday, ERole role, EStatus eStatus, String imgurl, String language) {
        super(id, username, email, password, fullName, gender, country, phone, birthday, role, eStatus);
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

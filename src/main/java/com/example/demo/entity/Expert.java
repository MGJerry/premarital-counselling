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
    public String language;
    public String GgMeetUrl;

    public Expert(long id, String username, String email, String password, String fullName, EGender gender, String country, String address, String phone, LocalDate birthday, String imgurl, ERole role, EStatus eStatus, String bio, String language, String ggMeetUrl) {
        super(id, username, email, password, fullName, gender, country, address, phone, birthday, imgurl, role, eStatus, bio);
        this.language = language;
        GgMeetUrl = ggMeetUrl;
    }

    public Expert() {

    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGgMeetUrl() {
        return GgMeetUrl;
    }

    public void setGgMeetUrl(String ggMeetUrl) {
        GgMeetUrl = ggMeetUrl;
    }
}

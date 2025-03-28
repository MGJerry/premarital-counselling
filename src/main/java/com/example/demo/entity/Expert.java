package com.example.demo.entity;

import com.example.demo.enums.EGender;
import com.example.demo.enums.EStatus;
import com.example.demo.model.ERole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

import java.time.LocalDate;

@DiscriminatorValue("EXPERT")
@Entity
public class Expert extends User{
    public String language;
    public String GgMeetUrl;
    
    // Rating attributes
    private Double averageRating;
    private Integer totalRatings;
    
    public Expert(long id, String username, String email, String password, String fullName, EGender gender, String country, String address, String phone, LocalDate birthday, String imgurl, ERole role, EStatus eStatus, String bio, String language, String ggMeetUrl) {
        super(id, username, email, password, fullName, gender, country, address, phone, birthday, imgurl, role, eStatus, bio);
        this.language = language;
        GgMeetUrl = ggMeetUrl;
        this.averageRating = 0.0;
        this.totalRatings = 0;
    }

    public Expert() {
        this.averageRating = 0.0;
        this.totalRatings = 0;
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
    
    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }
    
    // Method to update rating when a new feedback is added
    public void updateRating(int newRating) {
        if (totalRatings == null) totalRatings = 0;
        if (averageRating == null) averageRating = 0.0;
        
        double totalScore = averageRating * totalRatings;
        totalRatings++;
        averageRating = (totalScore + newRating) / totalRatings;
    }
}

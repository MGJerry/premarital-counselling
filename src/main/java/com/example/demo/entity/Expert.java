package com.example.demo.entity;

import com.example.demo.model.Member;
import com.example.demo.model.Specialization;
import jakarta.persistence.*;


@Entity
@Table(name = "experts")
public class Expert {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public String language;
    public String GgMeetUrl;
    // Rating attributes
    private Double averageRating;
    private Integer totalRatings;

    //Specialization
    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    private int specializationLevel;

    public int getSpecializationLevel() {
        return specializationLevel;
    }

    public void setSpecializationLevel(int specializationLevel) {
        this.specializationLevel = specializationLevel;
    }

    public Expert(Long id, User user, Member member, String language, String ggMeetUrl, Double averageRating, Integer totalRatings, Specialization specialization, int specializationLevel) {
        this.id = id;
        this.user = user;
        this.member = member;
        this.language = language;
        GgMeetUrl = ggMeetUrl;
        this.averageRating = averageRating;
        this.totalRatings = totalRatings;
        this.specialization = specialization;
        this.specializationLevel = specializationLevel;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

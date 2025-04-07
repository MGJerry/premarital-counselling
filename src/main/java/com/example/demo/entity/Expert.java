package com.example.demo.entity;

import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.Member;
import com.example.demo.model.Specialization;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "experts")
public class Expert {
    @Id
    private Long id;
    public String language;
    public String GgMeetUrl;
    public BigDecimal consultingPrice;
    @Column(nullable = false)
    private BigDecimal commission = BigDecimal.ZERO;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    // Rating attributes
    private Double averageRating;
    private Integer totalRatings;

    // Many-to-many relationship with assessment categories
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "expert_categories",
        joinColumns = @JoinColumn(name = "expert_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<AssessmentCategory> categories = new HashSet<>();

    // Keep specialization temporarily for backward compatibility
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

    // New methods to handle categories
    public Set<AssessmentCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<AssessmentCategory> categories) {
        this.categories = categories;
    }

    public void addCategory(AssessmentCategory category) {
        this.categories.add(category);
    }

    public void removeCategory(AssessmentCategory category) {
        this.categories.remove(category);
    }

    public Expert(Long id, String language, String ggMeetUrl, BigDecimal consultingPrice, BigDecimal commission, User user, Member member, Double averageRating, Integer totalRatings, Specialization specialization, int specializationLevel) {
        this.id = id;
        this.language = language;
        GgMeetUrl = ggMeetUrl;
        this.consultingPrice = consultingPrice;
        this.commission = commission;
        this.user = user;
        this.member = member;
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

    public BigDecimal getConsultingPrice() {
        return consultingPrice;
    }

    public void setConsultingPrice(BigDecimal consultingPrice) {
        this.consultingPrice = consultingPrice;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    // Method to update rating when a new feedback is added
    public void updateRating(int newRating) {
        if (totalRatings == null) totalRatings = 0;
        if (averageRating == null) averageRating = 0.0;
        
        double totalScore = averageRating * totalRatings;
        totalRatings++;
        averageRating = (totalScore + newRating) / totalRatings;
    }
    
    //Method to update commision when appointment status is true
    public void addCommissionFromConsulting() {
        if (consultingPrice != null) {
            BigDecimal twentyPercent = consultingPrice.multiply(BigDecimal.valueOf(0.2));
            this.commission = this.commission.add(twentyPercent);
        }
    }
}

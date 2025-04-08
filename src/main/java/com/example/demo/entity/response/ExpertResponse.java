package com.example.demo.entity.response;

import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.enums.EStatus;
import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.ERole;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ExpertResponse {
    private Long id;
    private String language;
    private String ggMeetUrl;
    private BigDecimal consultingPrice;
    private BigDecimal commission;
    private Double averageRating;
    private Integer totalRatings;
    private int specializationLevel;
    private Set<AssessmentCategory> categories;
    
    // User information
    private String fullName;
    private String email;
    private String phone;
    private String imgurl;
    private ERole role;
    private EStatus eStatus;
    
    // Nested user response
    private UserResponse user;
    
    public static ExpertResponse fromExpert(Expert expert) {
        ExpertResponse response = new ExpertResponse();
        response.setId(expert.getId());
        response.setLanguage(expert.getLanguage());
        response.setGgMeetUrl(expert.getGgMeetUrl());
        response.setConsultingPrice(expert.getConsultingPrice());
        response.setCommission(expert.getCommission());
        response.setAverageRating(expert.getAverageRating());
        response.setTotalRatings(expert.getTotalRatings());
        response.setSpecializationLevel(expert.getSpecializationLevel());
        response.setCategories(expert.getCategories());
        
        if (expert.getUser() != null) {
            User user = expert.getUser();
            response.setFullName(user.getFullName());
            response.setEmail(user.getEmail());
            response.setPhone(user.getPhone());
            response.setImgurl(user.getImgurl());
            response.setRole(user.getRole());
            response.setEStatus(user.geteStatus());
        }
        
        return response;
    }
} 
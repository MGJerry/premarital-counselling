package com.example.demo.entity.response;

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
} 
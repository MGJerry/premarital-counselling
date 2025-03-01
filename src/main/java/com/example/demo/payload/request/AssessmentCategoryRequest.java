package com.example.demo.payload.request;

import jakarta.validation.constraints.NotBlank;

public class AssessmentCategoryRequest {
    @NotBlank
    public String name;
    public String description;
    public Double weight;
    public String status;
}

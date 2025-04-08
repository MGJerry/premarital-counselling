package com.example.demo.entity.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class ExpertUpdateRequest {
    @NotEmpty(message = "Category IDs cannot be empty")
    private List<Long> categoryIds;

    @NotNull(message = "Consulting price cannot be null")
    @Positive(message = "Consulting price must be positive")
    private Double consultingPrice;
} 
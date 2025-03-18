package com.example.demo.payload.request;

import jakarta.validation.constraints.NotBlank;

public class ServicePackageRequest {
    @NotBlank
    public String name;
    public String description;
    public Integer sessionCount;
    public Integer validityDays;
    public Double price;
    public Double commissionFee;
    public Double discountPercentage;
    public String requirements;
    public String status;
}

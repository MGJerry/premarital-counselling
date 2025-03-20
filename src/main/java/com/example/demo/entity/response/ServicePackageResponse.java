package com.example.demo.entity.response;

import java.math.BigDecimal;

public class ServicePackageResponse {
    public Integer packageId;
    public String name;
    public String description;
    public Integer sessionCount;
    public Integer validityDays;
    public BigDecimal price;
    public BigDecimal commissionFee;
    public BigDecimal discountPercentage;
    public String requirements;
    public String status;
    public BigDecimal total;
    public BigDecimal commissionShare;
    public BigDecimal expertShare;

    public ServicePackageResponse() {
    }

    public ServicePackageResponse(Integer packageId, String name, String description, Integer sessionCount, Integer validityDays, BigDecimal price, BigDecimal commissionFee, BigDecimal discountPercentage, String requirements, String status, BigDecimal total, BigDecimal commissionShare, BigDecimal expertShare) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.sessionCount = sessionCount;
        this.validityDays = validityDays;
        this.price = price;
        this.commissionFee = commissionFee;
        this.discountPercentage = discountPercentage;
        this.requirements = requirements;
        this.status = status;
        this.total = total;
        this.commissionShare = commissionShare;
        this.expertShare = expertShare;
    }
}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ServicePackages")
@Getter
@Setter
public class ServicePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer packageId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer sessionCount;

    private Integer validityDays;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal commissionFee;

    @Column(precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(length = 50)
    private String status;

    public ServicePackage() {
    }

    public ServicePackage(Integer packageId, String name, String description, Integer sessionCount, Integer validityDays, BigDecimal price, BigDecimal commissionFee, BigDecimal discountPercentage, String requirements, String status) {
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
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Integer getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(Integer validityDays) {
        this.validityDays = validityDays;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

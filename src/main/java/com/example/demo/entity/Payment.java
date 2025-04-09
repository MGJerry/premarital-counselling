package com.example.demo.entity;

import com.example.demo.model.Member;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String txnRef;
    private LocalDateTime createdAt;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Payment() {
    }

    public Payment(Long id, String status, String txnRef, LocalDateTime createdAt, BigDecimal amount, Expert expert, Member member) {
        this.id = id;
        this.status = status;
        this.txnRef = txnRef;
        this.createdAt = createdAt;
        this.amount = amount;
        this.expert = expert;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxnRef() {
        return txnRef;
    }

    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

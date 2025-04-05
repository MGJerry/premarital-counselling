package com.example.demo.model;

import com.example.demo.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "members")
public class Member {
    @Id
    private Long memberId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String membershipType;
    private LocalDateTime joinedAt = LocalDateTime.now();

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Member() {};

    public Member(Long memberId, User user, String membershipType, LocalDateTime joinedAt) {
        this.memberId = memberId;
        this.user = user;
        this.membershipType = membershipType;
        this.joinedAt = joinedAt;
    }
}

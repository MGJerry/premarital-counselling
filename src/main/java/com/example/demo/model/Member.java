package com.example.demo.model;

import com.example.demo.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String membershipType;
    private LocalDateTime joinedAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

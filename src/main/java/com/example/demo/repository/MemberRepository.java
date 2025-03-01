package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.model.Member;
//import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUser (User user);
    Boolean existsByUser (User user);
}

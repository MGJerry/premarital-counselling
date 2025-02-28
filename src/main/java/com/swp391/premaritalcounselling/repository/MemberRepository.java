package com.swp391.premaritalcounselling.repository;

import com.swp391.premaritalcounselling.models.Member;
import com.swp391.premaritalcounselling.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUser (User user);
    Boolean existsByUser (User user);
}

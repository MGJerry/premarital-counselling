package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<User, Integer> {
    User findById(long id);

    Optional<User> findByUsername(String username);
}

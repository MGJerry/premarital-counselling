package com.example.demo.repository;

import java.util.Optional;

//import com.example.demo.model.User;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByFullName(String fullName);

  Boolean existsByFullName(String fullName);

  Boolean existsByEmail(String email);
}

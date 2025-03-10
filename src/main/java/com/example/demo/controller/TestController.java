package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.util.AuthenUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@SecurityRequirement(name = "api")
public class TestController {

  @GetMapping
  public ResponseEntity<User> getUserDetails() {
    return new ResponseEntity<>(AuthenUtil.getAuthenticatedUser(), HttpStatus.OK );
  }

  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/expert")
  @PreAuthorize("hasRole('EXPERT')")
  public String moderatorAccess() {
    return "Expert Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
}

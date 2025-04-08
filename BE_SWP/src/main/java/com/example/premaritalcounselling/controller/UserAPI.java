package com.example.premaritalcounselling.controller;

import com.example.premaritalcounselling.entity.User;
import com.example.premaritalcounselling.entity.request.AuthenticationRequest;
import com.example.premaritalcounselling.entity.request.UpdateRequest;
import com.example.premaritalcounselling.entity.request.UserRegisterRequest;
import com.example.premaritalcounselling.entity.response.AuthenticationResponse;
import com.example.premaritalcounselling.entity.response.UserResponse;
import com.example.premaritalcounselling.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "User Management", description = "APIs for managing users")
@SecurityRequirement(name = "api")
public class UserAPI {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        User newUser = authenticationService.register(userRegisterRequest);
        return ResponseEntity.ok(newUser);
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

    @Operation(summary = "Update user profile")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateRequest updateRequest, @PathVariable long id) {
        User user = authenticationService.updateProfile(updateRequest, id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get current user details")
    @GetMapping("/user/getCurrentUser")
    public ResponseEntity<?> getUserDetails() {
        return new ResponseEntity<>(authenticationService.getCurrentUser(), HttpStatus.OK);
    }

    @Operation(summary = "Request password reset")
    @PostMapping("/resetPassword")
    public ResponseEntity<?> requestPasswordReset(@RequestBody String email) {
        return authenticationService.createPasswordResetTokenForUser(email);
    }

    @Operation(summary = "Reset password with token")
    @PutMapping("/resetPassword/{token}")
    public ResponseEntity<?> resetPassword(@RequestBody String password, @PathVariable String token) {
        return authenticationService.resetPassword(token, password);
    }

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        authenticationService.deleteUserById(id);
        return ResponseEntity.ok("Delete Successfully");
    }

    @Operation(summary = "Create admin account")
    @PostMapping("/AdminAccount")
    public ResponseEntity<?> createAdminAccount(@RequestBody UserRegisterRequest userRegisterRequest) {
        User newAdmin = authenticationService.createAdmin(userRegisterRequest);
        return ResponseEntity.ok(newAdmin);
    }

    @Operation(summary = "Get all users")
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = authenticationService.getAllUsers();
        List<UserResponse> userResponses = users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
        return ResponseEntity.ok(userResponses);
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        Optional<User> user = authenticationService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Deactivate a user")
    @PutMapping("/deactivateUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deactivateUser(@PathVariable long id) {
        User user = authenticationService.deactivateUser(id);
        UserResponse response = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Activate a user")
    @PutMapping("/activateUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> activateUser(@PathVariable long id) {
        User user = authenticationService.activateUser(id);
        UserResponse response = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(response);
    }
} 
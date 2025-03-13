package com.example.demo.api;

import com.example.demo.entity.User;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.request.UpdateRequest;
import com.example.demo.entity.request.UserRegisterRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserAPI {
    @Autowired
    AuthenticationService authenticationService;

    //register
    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody UserRegisterRequest user){
        User newUser = authenticationService.register(user);
        return ResponseEntity.ok(newUser);
    }

    //login
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<User> update(@RequestBody UpdateRequest updateRequest, @PathVariable long id){
        User user = authenticationService.updateProfile(updateRequest, id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("user/getCurrentUser")
    @SecurityRequirement(name = "api")
    public ResponseEntity<User> getUserDetails() {
        return new ResponseEntity<>(authenticationService.getCurrentUser(), HttpStatus.OK );
    }

    @PostMapping("resetPassword")
    public ResponseEntity<String> requestPasswordReset(@RequestBody String email) {
        return authenticationService.createPasswordResetTokenForUser(email);
    }

    @PutMapping("resetPassword/{token}")
    public ResponseEntity<String> requestPasswordReset(@RequestBody String password, @PathVariable String token) {
        return authenticationService.resetPassword(token, password);
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        authenticationService.deleteUserById(id);
        return ResponseEntity.ok("Delete Successfully");
    }
    @PostMapping("/AdminAccount")
    public ResponseEntity createAdminAccount(@RequestBody UserRegisterRequest userRegisterRequest) {
        User newAdmin = authenticationService.createAdmin(userRegisterRequest);
        return ResponseEntity.ok(newAdmin);
    }
}

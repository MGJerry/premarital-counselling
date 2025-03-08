package com.example.demo.api;

import com.example.demo.entity.User;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.request.UserRegisterRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    
}

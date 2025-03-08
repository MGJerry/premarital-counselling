package com.example.demo.api;

import com.example.demo.entity.Expert;
import com.example.demo.entity.request.ExpertRegisterRequest;
import com.example.demo.service.ExpertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExpertAPI {
    @Autowired
    ExpertService expertService;
    //register for Expert
    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody ExpertRegisterRequest expertRegisterRequest){
        Expert newexpert = expertService.register(expertRegisterRequest);
        return ResponseEntity.ok(newexpert);
    }
}

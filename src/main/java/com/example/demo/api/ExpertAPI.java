package com.example.demo.api;

import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.entity.request.ExpertRegisterRequest;
import com.example.demo.entity.request.UpdateRequest;
import com.example.demo.repository.ExpertRepository;
import com.example.demo.service.ExpertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ExpertAPI {
    @Autowired
    ExpertService expertService;
    //register for Expert
    @PostMapping("expertregister")
    public ResponseEntity register(@Valid @RequestBody ExpertRegisterRequest expertRegisterRequest){
        Expert newexpert = expertService.register(expertRegisterRequest);
        return ResponseEntity.ok(newexpert);
    }
    @GetMapping("/getExpertById/{id}")
    public ResponseEntity<Optional<Expert>> getExpert(@PathVariable long id){
        Optional<Expert> expert = expertService.getExpertById(id);
        return ResponseEntity.ok(expert);
    }
    @GetMapping("/getAllExpert")
    public ResponseEntity<List<Expert>> getAllExpert(){
        List<Expert> experts = expertService.getAllExperts();
        return ResponseEntity.ok(experts);
    }
    @PutMapping ("/approveExpert/{id}")
    public ResponseEntity<Expert> approve(@PathVariable long id){
        Expert expert = expertService.approveExpert( id);
        return ResponseEntity.ok(expert);
    }
}

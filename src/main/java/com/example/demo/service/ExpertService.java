package com.example.demo.service;

import com.example.demo.entity.Expert;
import com.example.demo.entity.request.ExpertRegisterRequest;
import com.example.demo.enums.EStatus;
import com.example.demo.model.ERole;
import com.example.demo.repository.ExpertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertService {
    @Autowired
    ExpertRepository expertRepository;
    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;
    public Expert register(ExpertRegisterRequest expertRegisterRequest){
        Expert expert = new Expert();
        expert.setFullName(expertRegisterRequest.getFullName());
        expert.setUsername(expertRegisterRequest.getUserName());
        expert.setPhone(expertRegisterRequest.getPhone());
        expert.setImgurl(expertRegisterRequest.getImgurl());
        expert.setEmail(expertRegisterRequest.getEmail());
        expert.setPassword(passwordEncoder.encode(expertRegisterRequest.getPassword()));
        expert.setRole(ERole.ROLE_EXPERT);
        expert.seteStatus(EStatus.PENDING);

        Expert newExpert = expertRepository.save(expert);
        return newExpert;
    }
    public Optional<Expert> getExpertById(long id){
        return expertRepository.findById(id);
    }

}

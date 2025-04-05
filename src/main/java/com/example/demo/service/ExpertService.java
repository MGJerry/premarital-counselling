package com.example.demo.service;

import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.entity.request.ExpertRegisterRequest;
import com.example.demo.enums.EStatus;
import com.example.demo.model.ERole;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.ExpertRepository;
import com.example.demo.repository.SpecializationRepository;
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
    SpecializationRepository specializationRepository;
    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    public Expert register(ExpertRegisterRequest expertRegisterRequest){
        User user = new User();
        user.setFullName(expertRegisterRequest.getFullName());
        user.setUsername(expertRegisterRequest.getUserName());
        user.setPhone(expertRegisterRequest.getPhone());
        user.setImgurl(expertRegisterRequest.getImgurl());
        user.setEmail(expertRegisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(expertRegisterRequest.getPassword()));
        user.setRole(ERole.ROLE_EXPERT);
        user.seteStatus(EStatus.PENDING);


        User savedUser = authenticationRepository.save(user);

        Expert expert = new Expert();
        expert.setUser(savedUser);
        expert.setId(savedUser.getId());
        expert.setSpecialization(specializationRepository.findByName(expertRegisterRequest.getSpecialization()).orElseThrow(() -> new RuntimeException("Specialization not found")));
        expert.setSpecializationLevel(expertRegisterRequest.getSpecializationLevel());

        Expert savedExpert = expertRepository.save(expert);
        return savedExpert;
    }
    public Optional<Expert> getExpertById(long id){
        return expertRepository.findById(id);
    }

    public List<Expert> getAllExperts(){
        return expertRepository.findAll();
    }

    public Expert approveExpert(long id){
        Expert approvedExpert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        User user = approvedExpert.getUser();
        user.seteStatus(EStatus.APPROVED);
        authenticationRepository.save(user);
        return approvedExpert;
    }
    public Expert updateMeetingUrl(String url, long id){
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        expert.setGgMeetUrl(url);
        return expertRepository.save(expert);
    }
    public String getMeetingUrl(long id){
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return expert.getGgMeetUrl();
    }
}
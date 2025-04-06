package com.example.demo.service;

import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.entity.request.ExpertRegisterRequest;
import com.example.demo.enums.EStatus;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.ERole;
import com.example.demo.model.Specialization;
import com.example.demo.repository.AssessmentCategoryRepository;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.ExpertRepository;
import com.example.demo.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExpertService {
    @Autowired
    ExpertRepository expertRepository;
    @Autowired
    SpecializationRepository specializationRepository;
    @Autowired
    AssessmentCategoryRepository assessmentCategoryRepository;
    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    public Expert register(ExpertRegisterRequest expertRegisterRequest){
        if (authenticationRepository.existsByEmail(expertRegisterRequest.getEmail())) {
            throw new DuplicateEmailException("The email already exists.");
        }
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
        
        // First try to find the specialization by category name
        Optional<AssessmentCategory> category = assessmentCategoryRepository.findByName(expertRegisterRequest.getSpecialization());
        
        try {
            if (category.isPresent()) {
                System.out.println("Found category: " + category.get().getName() + " with ID: " + category.get().getCategoryId());
                // If category exists, find or create a default specialization for this category
                Optional<Specialization> specialization = specializationRepository.findFirstByCategory(category.get());
                if (specialization.isPresent()) {
                    System.out.println("Using specialization: " + specialization.get().getName() + " for category: " + category.get().getName());
                    expert.setSpecialization(specialization.get());
                } else {
                    // If no specialization found for this category, look for one by name
                    Optional<Specialization> specByName = specializationRepository.findByName(expertRegisterRequest.getSpecialization());
                    if (specByName.isPresent()) {
                        System.out.println("Using specialization by name: " + specByName.get().getName());
                        expert.setSpecialization(specByName.get());
                    } else {
                        // Still didn't find a specialization, throw a more specific error
                        throw new RuntimeException("No specialization found for category: " + category.get().getName() + 
                                                  ". Please contact system administrator.");
                    }
                }
            } else {
                // If no category found, try finding specialization directly by name
                Optional<Specialization> specialization = specializationRepository.findByName(expertRegisterRequest.getSpecialization());
                if (specialization.isPresent()) {
                    System.out.println("Using direct specialization: " + specialization.get().getName());
                    expert.setSpecialization(specialization.get());
                } else {
                    // Neither category nor specialization found - throw detailed error
                    throw new RuntimeException("Specialization or category '" + expertRegisterRequest.getSpecialization() + 
                                              "' not found. Please select a valid specialization from the dropdown.");
                }
            }
        } catch (Exception e) {
            // Delete the user if specialization assignment fails
            authenticationRepository.delete(savedUser);
            
            // Re-throw the exception with diagnostic info
            if (e instanceof RuntimeException) {
                throw e;
            } else {
                throw new RuntimeException("Error assigning specialization: " + e.getMessage());
            }
        }
        
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
    
    public Expert updateConsultingPrice(long id){
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(expert.getSpecializationLevel()==1){
            expert.setConsultingPrice(BigDecimal.valueOf(500));
        } else if(expert.getSpecializationLevel()==2){
            expert.setConsultingPrice(BigDecimal.valueOf(750));
        } else if(expert.getSpecializationLevel()==3){
            expert.setConsultingPrice(BigDecimal.valueOf(1000));
        }
        expertRepository.save(expert);
        return expert;
    }
}
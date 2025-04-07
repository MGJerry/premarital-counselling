package com.example.demo.service;

import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.entity.request.ExpertRegisterRequest;
import com.example.demo.enums.EStatus;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.model.AssessmentCategory;
import com.example.demo.model.ERole;
import com.example.demo.repository.AssessmentCategoryRepository;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.ExpertRepository;
import com.example.demo.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    
    @Autowired
    private AssessmentCategoryRepository assessmentCategoryRepository;

    @Transactional
    public Expert register(ExpertRegisterRequest expertRegisterRequest){
        if (authenticationRepository.existsByEmail(expertRegisterRequest.getEmail())) {
            throw new DuplicateEmailException("The email already exists.");
        }
        
        // Create and save User entity
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

        // Create Expert entity
        Expert expert = new Expert();
        expert.setUser(savedUser);
        expert.setId(savedUser.getId());
        
        // Set specialization level
        expert.setSpecializationLevel(expertRegisterRequest.getSpecializationLevel());
        
        // Set consulting price based on specialization level
        if(expert.getSpecializationLevel() == 1) {
            expert.setConsultingPrice(BigDecimal.valueOf(500));
        } else if(expert.getSpecializationLevel() == 2) {
            expert.setConsultingPrice(BigDecimal.valueOf(750));
        } else if(expert.getSpecializationLevel() == 3) {
            expert.setConsultingPrice(BigDecimal.valueOf(1000));
        }
        
        // Find and add all requested categories with improved error handling
        Set<AssessmentCategory> categories = new HashSet<>();
        List<Long> notFoundCategories = new ArrayList<>();
        
        for (Long categoryId : expertRegisterRequest.getCategoryIds()) {
            try {
                Optional<AssessmentCategory> categoryOpt = assessmentCategoryRepository.findByCategoryId(categoryId);
                if (categoryOpt.isPresent()) {
                    categories.add(categoryOpt.get());
                } else {
                    notFoundCategories.add(categoryId);
                }
            } catch (Exception e) {
                notFoundCategories.add(categoryId);
            }
        }
        
        // If any categories were not found, throw a meaningful exception
        if (!notFoundCategories.isEmpty()) {
            String errorMessage = "The following category IDs could not be found: " + 
                    String.join(", ", notFoundCategories.stream().map(String::valueOf).collect(Collectors.toList()));
                    
            // Delete the user since expert registration failed
            authenticationRepository.delete(savedUser);
            
            throw new IllegalArgumentException(errorMessage);
        }
        
        expert.setCategories(categories);
        
        // Save and return the expert
        try {
            Expert savedExpert = expertRepository.saveAndFlush(expert);
            return savedExpert;
        } catch (Exception e) {
            // If expert saving fails, clean up by deleting the user
            authenticationRepository.delete(savedUser);
            throw new RuntimeException("Failed to save expert: " + e.getMessage(), e);
        }
    }
    
    public Optional<Expert> getExpertById(long id){
        return expertRepository.findById(id);
    }

    public List<Expert> getAllExperts(){
        return expertRepository.findAll();
    }

    @Transactional
    public Expert approveExpert(long id){
        Expert approvedExpert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        User user = approvedExpert.getUser();
        user.seteStatus(EStatus.APPROVED);
        authenticationRepository.save(user);
        return approvedExpert;
    }
    
    @Transactional
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

    @Transactional
    public Expert updateConsultingPrice(long id){
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        
        // Update consulting price based on specialization level
        if(expert.getSpecializationLevel() == 1){
            expert.setConsultingPrice(BigDecimal.valueOf(500));
        } else if(expert.getSpecializationLevel() == 2){
            expert.setConsultingPrice(BigDecimal.valueOf(750));
        } else if(expert.getSpecializationLevel() == 3){
            expert.setConsultingPrice(BigDecimal.valueOf(1000));
        }
        
        // Explicitly merge the entity to handle detached entities
        return expertRepository.saveAndFlush(expert);
    }
    
    // New method to find experts by category
    public List<Expert> getExpertsByCategory(AssessmentCategory category) {
        return expertRepository.findAll().stream()
            .filter(expert -> expert.getCategories().contains(category))
            .collect(Collectors.toList());
    }
}
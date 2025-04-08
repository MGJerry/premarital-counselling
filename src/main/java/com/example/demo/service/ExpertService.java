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
        
        // Create Expert entity first
        Expert expert = new Expert();
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
            throw new IllegalArgumentException(errorMessage);
        }
        
        expert.setCategories(categories);
        
        // Set the bidirectional relationship
        expert.setUser(user);
        user.setExpert(expert);
        
        // Save the user which will cascade to expert
        User savedUser = authenticationRepository.save(user);
        return savedUser.getExpert();
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
    public Expert updateConsultingPrice(long id, BigDecimal customPrice){
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        
        if (customPrice != null) {
            // Use the custom price provided
            expert.setConsultingPrice(customPrice);
        } else {
            // Update based on specialization level (default behavior)
            if(expert.getSpecializationLevel() == 1){
                expert.setConsultingPrice(BigDecimal.valueOf(500000));
            } else if(expert.getSpecializationLevel() == 2){
                expert.setConsultingPrice(BigDecimal.valueOf(750000));
            } else if(expert.getSpecializationLevel() == 3){
                expert.setConsultingPrice(BigDecimal.valueOf(1000000));
            }
        }
        
        // Explicitly merge the entity to handle detached entities
        return expertRepository.saveAndFlush(expert);
    }
    
    @Transactional
    public Expert updateExpertCommission(long id, double commissionRate) {
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        
        // Convert percentage to decimal (e.g., 20% -> 0.2)
        BigDecimal commissionDecimal = BigDecimal.valueOf(commissionRate / 100.0);
        
        // Set the commission rate
        expert.setCommission(commissionDecimal);
        
        return expertRepository.saveAndFlush(expert);
    }
    
    // New method to find experts by category
    public List<Expert> getExpertsByCategory(AssessmentCategory category) {
        return expertRepository.findAll().stream()
            .filter(expert -> expert.getCategories().contains(category))
            .collect(Collectors.toList());
    }
    
    @Transactional
    public Expert rejectExpert(long id) {
        Expert rejectedExpert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        User user = rejectedExpert.getUser();
        user.seteStatus(EStatus.REJECTED);
        authenticationRepository.save(user);
        return rejectedExpert;
    }
    
    @Transactional
    public void deleteExpert(long id) {
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        
        // Get the user associated with this expert
        User user = expert.getUser();
        
        // Delete the expert first
        expertRepository.delete(expert);
        
        // Delete the user if it exists
        if (user != null) {
            authenticationRepository.delete(user);
        }
    }
    
    @Transactional
    public Expert updateSpecializationLevel(long id, int specializationLevel) {
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        
        // Update specialization level
        expert.setSpecializationLevel(specializationLevel);
        
        // Update consulting price based on new specialization level
        if(specializationLevel == 1) {
            expert.setConsultingPrice(BigDecimal.valueOf(500000));
        } else if(specializationLevel == 2) {
            expert.setConsultingPrice(BigDecimal.valueOf(750000));
        } else if(specializationLevel == 3) {
            expert.setConsultingPrice(BigDecimal.valueOf(1000000));
        }
        
        return expertRepository.saveAndFlush(expert);
    }

    public Expert updateExpertCategories(Long expertId, List<Long> categoryIds, Double consultingPrice) {
        Expert expert = expertRepository.findById(expertId)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        
        List<AssessmentCategory> categories = assessmentCategoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new RuntimeException("One or more categories not found");
        }
        
        Set<AssessmentCategory> categorySet = new HashSet<>(categories);
        expert.setCategories(categorySet);
        expert.setConsultingPrice(BigDecimal.valueOf(consultingPrice));
        
        return expertRepository.save(expert);
    }
}
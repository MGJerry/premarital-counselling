package com.example.demo.api;

import com.example.demo.entity.Expert;
import com.example.demo.entity.User;
import com.example.demo.entity.request.ExpertRegisterRequest;
import com.example.demo.entity.response.ExpertResponse;
import com.example.demo.entity.request.ExpertUpdateRequest;
import com.example.demo.entity.response.UserResponse;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.service.ExpertService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ExpertAPI {
    private static final Logger logger = Logger.getLogger(ExpertAPI.class.getName());
    
    @Autowired
    ExpertService expertService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    //register for Expert
    @PostMapping("expertregister")
    public ResponseEntity<?> register(@Valid @RequestBody ExpertRegisterRequest expertRegisterRequest){
        logger.info("Received expert registration request: " + expertRegisterRequest.getEmail());
        try {
            logger.info("Processing registration for: " + expertRegisterRequest.getEmail());
            logger.info("Selected categories: " + expertRegisterRequest.getCategoryIds());
            
            Expert newexpert = expertService.register(expertRegisterRequest);
            logger.info("Expert registration successful for: " + expertRegisterRequest.getEmail());
            
            ExpertResponse response = modelMapper.map(newexpert, ExpertResponse.class);
            // Map user information
            if (newexpert.getUser() != null) {
                response.setFullName(newexpert.getUser().getFullName());
                response.setEmail(newexpert.getUser().getEmail());
                response.setPhone(newexpert.getUser().getPhone());
                response.setImgurl(newexpert.getUser().getImgurl());
                response.setRole(newexpert.getUser().getRole());
                response.setEStatus(newexpert.getUser().geteStatus());
            }
            return ResponseEntity.ok(response);
        } catch (DuplicateEmailException e) {
            logger.info("Registration failed: Duplicate email: " + expertRegisterRequest.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warning("Registration failed: Invalid arguments: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        } catch (Exception e) {
            // Log the full exception details for debugging
            logger.log(Level.SEVERE, "Unexpected error during expert registration", e);
            logger.severe("Error class: " + e.getClass().getName());
            logger.severe("Error message: " + e.getMessage());
            
            if (e.getCause() != null) {
                logger.severe("Cause: " + e.getCause().getMessage());
            }
            
            // Create a detailed error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Registration failed: " + e.getMessage());
            errorResponse.put("exceptionType", e.getClass().getName());
            if (e.getCause() != null) {
                errorResponse.put("cause", e.getCause().getMessage());
            }
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
    
    @GetMapping("/getExpertById/{id}")
    public ResponseEntity<ExpertResponse> getExpert(@PathVariable long id){
        Optional<Expert> expertOpt = expertService.getExpertById(id);
        if (expertOpt.isPresent()) {
            Expert expert = expertOpt.get();
            ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
            // Map user information
            if (expert.getUser() != null) {
                response.setFullName(expert.getUser().getFullName());
                response.setEmail(expert.getUser().getEmail());
                response.setPhone(expert.getUser().getPhone());
                response.setImgurl(expert.getUser().getImgurl());
                response.setRole(expert.getUser().getRole());
                response.setEStatus(expert.getUser().geteStatus());
            }
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/getAllExpert")
    public ResponseEntity<List<ExpertResponse>> getAllExpert(){
        List<Expert> experts = expertService.getAllExperts();
        List<ExpertResponse> expertResponses = experts.stream()
                .map(expert -> {
                    ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
                    // Map user information
                    if (expert.getUser() != null) {
                        response.setFullName(expert.getUser().getFullName());
                        response.setEmail(expert.getUser().getEmail());
                        response.setPhone(expert.getUser().getPhone());
                        response.setImgurl(expert.getUser().getImgurl());
                        response.setRole(expert.getUser().getRole());
                        response.setEStatus(expert.getUser().geteStatus());
                    }
                    return response;
                })
                .toList();
        return ResponseEntity.ok(expertResponses);
    }
    
    @PutMapping ("/approveExpert/{id}")
    public ResponseEntity<ExpertResponse> approve(@PathVariable long id){
        Expert expert = expertService.approveExpert(id);
        ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
        // Map user information
        if (expert.getUser() != null) {
            response.setFullName(expert.getUser().getFullName());
            response.setEmail(expert.getUser().getEmail());
            response.setPhone(expert.getUser().getPhone());
            response.setImgurl(expert.getUser().getImgurl());
            response.setRole(expert.getUser().getRole());
            response.setEStatus(expert.getUser().geteStatus());
        }
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/updateMeetingUrl/{id}")
    public ResponseEntity<ExpertResponse> creatMeetingUrl(@RequestBody String url, @PathVariable long id){
        Expert expert = expertService.updateMeetingUrl(url, id);
        ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
        // Map user information
        if (expert.getUser() != null) {
            response.setFullName(expert.getUser().getFullName());
            response.setEmail(expert.getUser().getEmail());
            response.setPhone(expert.getUser().getPhone());
            response.setImgurl(expert.getUser().getImgurl());
            response.setRole(expert.getUser().getRole());
            response.setEStatus(expert.getUser().geteStatus());
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getMeetingUrl/{id}")
    public ResponseEntity<String> getMeetingUrl(@PathVariable long id){
        String MeetingUrl = expertService.getMeetingUrl(id);
        return ResponseEntity.ok(MeetingUrl);
    }
    
    @PutMapping("/updateConsultingPrice/{id}")
    public ResponseEntity<ExpertResponse> updateConsultingPrice(@PathVariable long id, @RequestBody(required = false) Map<String, Object> requestBody){
        BigDecimal price = null;
        
        // Check if price is provided in the request body
        if (requestBody != null && requestBody.containsKey("price")) {
            try {
                price = new BigDecimal(requestBody.get("price").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        Expert expert = expertService.updateConsultingPrice(id, price);
        ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
        // Map user information
        if (expert.getUser() != null) {
            response.setFullName(expert.getUser().getFullName());
            response.setEmail(expert.getUser().getEmail());
            response.setPhone(expert.getUser().getPhone());
            response.setImgurl(expert.getUser().getImgurl());
            response.setRole(expert.getUser().getRole());
            response.setEStatus(expert.getUser().geteStatus());
        }
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/updateExpertCommission/{id}")
    public ResponseEntity<ExpertResponse> updateExpertCommission(@PathVariable long id, @RequestBody Map<String, Object> requestBody) {
        // Extract commission rate from request body
        if (!requestBody.containsKey("commissionRate")) {
            return ResponseEntity.badRequest().build();
        }
        
        double commissionRate;
        try {
            commissionRate = Double.parseDouble(requestBody.get("commissionRate").toString());
            // Validate commission rate (0-100%)
            if (commissionRate < 0 || commissionRate > 100) {
                return ResponseEntity.badRequest().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
        
        Expert expert = expertService.updateExpertCommission(id, commissionRate);
        ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
        // Map user information
        if (expert.getUser() != null) {
            response.setFullName(expert.getUser().getFullName());
            response.setEmail(expert.getUser().getEmail());
            response.setPhone(expert.getUser().getPhone());
            response.setImgurl(expert.getUser().getImgurl());
            response.setRole(expert.getUser().getRole());
            response.setEStatus(expert.getUser().geteStatus());
        }
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/rejectExpert/{id}")
    public ResponseEntity<ExpertResponse> rejectExpert(@PathVariable long id) {
        Expert expert = expertService.rejectExpert(id);
        ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
        // Map user information
        if (expert.getUser() != null) {
            response.setFullName(expert.getUser().getFullName());
            response.setEmail(expert.getUser().getEmail());
            response.setPhone(expert.getUser().getPhone());
            response.setImgurl(expert.getUser().getImgurl());
            response.setRole(expert.getUser().getRole());
            response.setEStatus(expert.getUser().geteStatus());
        }
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/deactivateExpert/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExpertResponse> deactivateExpert(@PathVariable long id) {
        Expert expert = expertService.deactivateExpert(id);
        ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
        // Map user information
        if (expert.getUser() != null) {
            response.setFullName(expert.getUser().getFullName());
            response.setEmail(expert.getUser().getEmail());
            response.setPhone(expert.getUser().getPhone());
            response.setImgurl(expert.getUser().getImgurl());
            response.setRole(expert.getUser().getRole());
            response.setEStatus(expert.getUser().geteStatus());
        }
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/deleteExpert/{id}")
    public ResponseEntity<String> deleteExpert(@PathVariable long id) {
        expertService.deleteExpert(id);
        return ResponseEntity.ok("Expert deleted successfully");
    }
    
    @PutMapping("/updateSpecializationLevel/{id}")
    public ResponseEntity<ExpertResponse> updateSpecializationLevel(@PathVariable long id, @RequestBody Map<String, Object> requestBody) {
        // Extract specialization level from request body
        if (!requestBody.containsKey("specializationLevel")) {
            return ResponseEntity.badRequest().build();
        }
        
        int specializationLevel;
        try {
            specializationLevel = Integer.parseInt(requestBody.get("specializationLevel").toString());
            // Validate specialization level (1-3)
            if (specializationLevel < 1 || specializationLevel > 3) {
                return ResponseEntity.badRequest().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
        
        Expert expert = expertService.updateSpecializationLevel(id, specializationLevel);
        ExpertResponse response = modelMapper.map(expert, ExpertResponse.class);
        // Map user information
        if (expert.getUser() != null) {
            response.setFullName(expert.getUser().getFullName());
            response.setEmail(expert.getUser().getEmail());
            response.setPhone(expert.getUser().getPhone());
            response.setImgurl(expert.getUser().getImgurl());
            response.setRole(expert.getUser().getRole());
            response.setEStatus(expert.getUser().geteStatus());
        }
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{expertId}/categories")
    public ResponseEntity<?> updateExpertCategories(
            @PathVariable Long expertId,
            @Valid @RequestBody ExpertUpdateRequest request) {
        try {
            Expert expert = expertService.updateExpertCategories(
                expertId,
                request.getCategoryIds(),
                request.getConsultingPrice()
            );
            
            ExpertResponse response = ExpertResponse.fromExpert(expert);
            if (expert.getUser() != null) {
                response.setUser(UserResponse.fromUser(expert.getUser()));
            }
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

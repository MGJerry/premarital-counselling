package com.example.demo.api;

import com.example.demo.entity.Expert;
import com.example.demo.entity.request.ExpertRegisterRequest;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.service.ExpertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    //register for Expert
    @PostMapping("expertregister")
    public ResponseEntity<?> register(@Valid @RequestBody ExpertRegisterRequest expertRegisterRequest){
        logger.info("Received expert registration request: " + expertRegisterRequest.getEmail());
        try {
            logger.info("Processing registration for: " + expertRegisterRequest.getEmail());
            logger.info("Selected categories: " + expertRegisterRequest.getCategoryIds());
            
            Expert newexpert = expertService.register(expertRegisterRequest);
            logger.info("Expert registration successful for: " + expertRegisterRequest.getEmail());
            return ResponseEntity.ok(newexpert);
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
        Expert expert = expertService.approveExpert(id);
        return ResponseEntity.ok(expert);
    }
    
    @PutMapping("/updateMeetingUrl/{id}")
    public ResponseEntity<Expert> creatMeetingUrl(@RequestBody String url, @PathVariable long id){
        Expert expert = expertService.updateMeetingUrl(url, id);
        return ResponseEntity.ok(expert);
    }
    
    @GetMapping("/getMeetingUrl/{id}")
    public ResponseEntity<String> getMeetingUrl(@PathVariable long id){
        String MeetingUrl = expertService.getMeetingUrl(id);
        return ResponseEntity.ok(MeetingUrl);
    }
    
    @PutMapping("/updateConsultingPrice/{id}")
    public ResponseEntity<Expert> updateConsultingPrice(@PathVariable long id){
        Expert expert = expertService.updateConsultingPrice(id);
        return ResponseEntity.ok(expert);
    }
}

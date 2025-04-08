package com.example.demo.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Example client to demonstrate how to use the expert update API endpoint.
 * This class is for demonstration purposes only.
 */
public class ExpertUpdateClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final String UPDATE_CATEGORIES_ENDPOINT = "/%d/categories";
    
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    
    public ExpertUpdateClient() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    
    /**
     * Updates an expert's categories and consulting price.
     * 
     * @param expertId The ID of the expert to update
     * @param categoryIds List of category IDs to assign to the expert
     * @param consultingPrice The new consulting price
     * @param authToken JWT auth token for authentication
     * @return The response from the server
     */
    public String updateExpertCategories(Long expertId, Long[] categoryIds, Double consultingPrice, String authToken) {
        // Set authorization header if token is provided
        if (authToken != null && !authToken.isEmpty()) {
            headers.set("Authorization", "Bearer " + authToken);
        }
        
        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("categoryIds", Arrays.asList(categoryIds));
        requestBody.put("consultingPrice", consultingPrice);
        
        // Create HTTP entity with headers and body
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        
        // Build URL
        String url = BASE_URL + String.format(UPDATE_CATEGORIES_ENDPOINT, expertId);
        
        // Make the request
        ResponseEntity<String> response = restTemplate.exchange(
            url, 
            org.springframework.http.HttpMethod.PUT, 
            entity, 
            String.class
        );
        
        return response.getBody();
    }
    
    /**
     * Example usage of the client
     */
    public static void main(String[] args) {
        ExpertUpdateClient client = new ExpertUpdateClient();
        
        // Example: Update expert with ID 1 to have categories 2, 3, 5 and a price of 850000
        Long expertId = 1L;
        Long[] categoryIds = {2L, 3L, 5L};
        Double price = 850000.0;
        String authToken = "your_jwt_token_here"; // Replace with actual token
        
        String result = client.updateExpertCategories(expertId, categoryIds, price, authToken);
        System.out.println("Response: " + result);
    }
} 
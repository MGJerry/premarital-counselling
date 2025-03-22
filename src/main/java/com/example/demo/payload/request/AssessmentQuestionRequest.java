package com.example.demo.payload.request;

import java.util.Map;

public class AssessmentQuestionRequest {
    public Long id;
    public String content;
    public String questionType;
    public Map<String, Double> options;
    public Integer answer;
    public Boolean required;
    public String status;
    public String category;
}

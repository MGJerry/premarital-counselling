package com.swp391.premaritalcounselling.payload.request;

import java.util.List;

public class AssessmentQuestionRequest {
    public Long id;
    public String content;
    public String questionType;
    public List<String> options;
    public Integer answer;
    public Double weight;
    public Boolean required;
    public String status;
    public String category;
}

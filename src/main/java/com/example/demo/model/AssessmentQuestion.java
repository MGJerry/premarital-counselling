package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "AssessmentQuestions")
public class AssessmentQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", unique = true, nullable = false)
    private Long questionId;

    private String content;
    private String questionType;
    @ElementCollection
    @CollectionTable(name = "assessment_question_options",
            joinColumns = @JoinColumn(name = "question_id"))
    @MapKeyColumn(name = "option_text")
    @Column(name = "weight")
    private Map<String, Double> options; // Key: option text, Value: weight
    private Integer answer;
    private Boolean required;
    private String status;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Map<String, Double> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Double> options) {
        this.options = options;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private AssessmentCategory category;

    public AssessmentCategory getCategory() {
        return category;
    }

    public void setCategory(AssessmentCategory category) {
        this.category = category;
    }
}

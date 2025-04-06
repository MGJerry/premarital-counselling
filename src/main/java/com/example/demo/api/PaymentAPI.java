package com.example.demo.api;

import com.example.demo.entity.Expert;
import com.example.demo.model.Member;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.ExpertRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.ExpertService;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentAPI {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ExpertRepository expertRepository;
    @Autowired
    private MemberRepository memberRepository;


    @GetMapping("/createPaymentUrl")
    public ResponseEntity<String> createPaymentUrl(@RequestParam Long expertId,
                                                   @RequestParam Long memberId) {
        try{
            Expert expert = expertRepository.findById(expertId)
                    .orElseThrow(() -> new RuntimeException("Expert not found"));

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            String paymentUrl = paymentService.createURLPayment(expert, member);
            return ResponseEntity.ok(paymentUrl);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

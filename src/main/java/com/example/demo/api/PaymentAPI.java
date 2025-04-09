package com.example.demo.api;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.Payment;
import com.example.demo.model.Member;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.ExpertRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api")
public class PaymentAPI {
    private static final Logger logger = LoggerFactory.getLogger(PaymentAPI.class);

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ExpertRepository expertRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Value("${vnpay.secretKey}")
    private String secretKey;

    @GetMapping("/createPaymentUrl")
    public ResponseEntity<String> createPaymentUrl(@RequestParam Long expertId,
                                                   @RequestParam Long memberId) {
        try{
            Expert expert = expertRepository.findById(expertId)
                    .orElseThrow(() -> new RuntimeException("Expert not found with ID: " + expertId));

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

            String paymentUrl = paymentService.createURLPayment(expert, member);
            logger.info("Generated VNPay URL for member {} and expert {}: {}", memberId, expertId, paymentUrl);
            return ResponseEntity.ok(paymentUrl);
        } catch (IllegalArgumentException e) {
             logger.error("Validation error creating payment URL: {}", e.getMessage());
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e){
            logger.error("Error creating VNPay URL: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment URL: " + e.getMessage());
        }
    }

    @GetMapping("/payments/vnpay-return")
    @Transactional
    public RedirectView handleVNPayReturn(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }

        String signValue = generateVNPayHash(fields);
        String txnRef = request.getParameter("txnRef");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String frontendRedirectUrl = "http://localhost:5173/";

        try {
            if (signValue.equals(vnp_SecureHash)) {
                Payment payment = paymentRepository.findByTxnRef(txnRef)
                        .orElseThrow(() -> new RuntimeException("Payment record not found for txnRef: " + txnRef));

                if (!"PENDING".equals(payment.getStatus())) {
                    logger.warn("Attempted to process already processed payment with txnRef: {}", txnRef);
                    return new RedirectView(frontendRedirectUrl + "payment-result?status=" + payment.getStatus().toLowerCase());
                }

                if ("00".equals(vnp_ResponseCode)) {
                    logger.info("VNPay payment success for txnRef: {}", txnRef);
                    payment.setStatus("SUCCESS");

                    Appointment appointment = new Appointment();
                    appointment.setMember(payment.getMember().getUser());
                    appointment.setExpert(payment.getExpert());
                    appointment.setAppointmentDateTime(payment.getCreatedAt());
                    appointment.setStatus(true);
                    appointment.setNotes("Created after successful payment " + txnRef);
                    appointmentRepository.save(appointment);
                    logger.info("Appointment created successfully for payment txnRef: {}", txnRef);

                    Expert expert = payment.getExpert();
                    expert.addCommissionFromConsulting();
                    expertRepository.save(expert);
                    logger.info("Commission updated for expert {} after payment txnRef: {}", expert.getId(), txnRef);

                    paymentRepository.save(payment);
                    return new RedirectView(frontendRedirectUrl + "payment-result?status=success&appointmentId=" + appointment.getId());

                } else {
                    logger.warn("VNPay payment failed for txnRef: {}. Response code: {}", txnRef, vnp_ResponseCode);
                    payment.setStatus("FAILED");
                    paymentRepository.save(payment);
                    return new RedirectView(frontendRedirectUrl + "payment-result?status=failed&reason=vnpay_error&code=" + vnp_ResponseCode);
                }
            } else {
                logger.error("VNPay hash mismatch for txnRef: {}. Received: {}, Expected: {}", txnRef, vnp_SecureHash, signValue);
                 Payment payment = paymentRepository.findByTxnRef(txnRef).orElse(null);
                 if (payment != null && "PENDING".equals(payment.getStatus())) {
                     payment.setStatus("HASH_MISMATCH");
                     paymentRepository.save(payment);
                 }
                return new RedirectView(frontendRedirectUrl + "payment-result?status=failed&reason=invalid_signature");
            }
        } catch (Exception e) {
            logger.error("Error processing VNPay return for txnRef {}: {}", txnRef, e.getMessage(), e);
            return new RedirectView(frontendRedirectUrl + "payment-result?status=failed&reason=server_error");
        }
    }

    private String generateVNPayHash(Map<String, String> vnp_Params) {
        Map<String, String> sortedMap = new TreeMap<>(vnp_Params);

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            try {
                signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
                signDataBuilder.append("=");
                signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
                signDataBuilder.append("&");
            } catch (UnsupportedEncodingException e) {
                logger.error("Error encoding VNPay parameters: {}", e.getMessage(), e);
                throw new RuntimeException("Error encoding VNPay parameters", e);
            }
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1);
        String signData = signDataBuilder.toString();

        try {
            Mac hmacSha512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmacSha512.init(keySpec);
            byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            for (byte b : hmacBytes) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("Error generating HMAC SHA512: {}", e.getMessage(), e);
            throw new RuntimeException("Error generating VNPay hash", e);
        }
    }
}

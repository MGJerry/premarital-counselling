package com.example.demo.service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Expert;
import com.example.demo.entity.Payment;
import com.example.demo.model.Member;
import com.example.demo.repository.ExpertRepository;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.secretKey}")
    private String secretKey;

    @Value("${vnpay.returnUrl}")
    private String configuredReturnUrl;

    private final String VNP_API_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private final String VNP_VERSION = "2.1.0";
    private final String VNP_COMMAND = "pay";
    private final String VNP_ORDER_TYPE = "other";
    private final String VNP_LOCALE = "vn";
    private final String VNP_CURR_CODE = "VND";

    @Transactional
    public String createURLPayment(Expert expert, Member member) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);
        String txnRef = UUID.randomUUID().toString().substring(0, 8);

        BigDecimal amount = expert.getConsultingPrice();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Consulting price must be positive.");
        }
        String vnpAmount = amount.multiply(BigDecimal.valueOf(100)).toBigInteger().toString();

        String returnUrlWithTxnRef = configuredReturnUrl + "?txnRef=" + txnRef;

        Payment payment = new Payment();
        payment.setExpert(expert);
        payment.setMember(member);
        payment.setTxnRef(txnRef);
        payment.setStatus("PENDING");
        payment.setCreatedAt(createDate);
        payment.setAmount(amount);
        paymentRepository.save(payment);

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", VNP_VERSION);
        vnpParams.put("vnp_Command", VNP_COMMAND);
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", VNP_LOCALE);
        vnpParams.put("vnp_CurrCode", VNP_CURR_CODE);
        vnpParams.put("vnp_TxnRef", txnRef);
        vnpParams.put("vnp_OrderInfo", "Payment for consultation service. Code: " + txnRef);
        vnpParams.put("vnp_OrderType", VNP_ORDER_TYPE);
        vnpParams.put("vnp_Amount", vnpAmount);
        vnpParams.put("vnp_ReturnUrl", returnUrlWithTxnRef);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "127.0.0.1");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1);
        String signData = signDataBuilder.toString();

        String signed = generateHMAC(secretKey, signData);
        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(VNP_API_URL);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);

        return urlBuilder.toString();
    }

    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    @Transactional
    public void updateCommission(Appointment appointment) {
        if (appointment != null && appointment.isStatus()) {
            Expert expert = appointment.getExpert();
            if (expert != null) {
                expert.addCommissionFromConsulting();
                expertRepository.save(expert);
            }
        }
    }
}

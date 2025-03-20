package com.example.demo.service;

import com.example.demo.entity.response.ServicePackageResponse;
import com.example.demo.model.ServicePackage;
import com.example.demo.payload.request.ServicePackageRequest;
import com.example.demo.repository.ServicePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ServicePackageService {
    @Autowired
    ServicePackageRepository serviceRepository;

    public ResponseEntity<List<ServicePackage>> getAllServices() {
        try {
            return new ResponseEntity<>(serviceRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<ServicePackage> getServiceById(Long id) {
        try {
            return new ResponseEntity<>(serviceRepository.findByPackageId(id).get(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ServicePackage(), BAD_REQUEST);
    }

    public ResponseEntity<String> createService(ServicePackageRequest request) {
        try {
            ServicePackage service = new ServicePackage();
            service.setName(request.name);
            service.setDescription(request.description);
            service.setSessionCount(request.sessionCount);
            service.setValidityDays(request.validityDays);
            service.setPrice(new BigDecimal(request.price));
            service.setCommissionFee(new BigDecimal(request.commissionFee));
            service.setDiscountPercentage(new BigDecimal(request.discountPercentage));
            service.setStatus(request.status);
            serviceRepository.save(service);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", BAD_REQUEST);
    }

    public ResponseEntity<ServicePackage> updateService(Long id, ServicePackageRequest request) {
        return serviceRepository.findById(id).map(service -> {
            service.setName(request.name);
            service.setDescription(request.description);
            service.setSessionCount(request.sessionCount);
            service.setValidityDays(request.validityDays);
            service.setPrice(new BigDecimal(request.price));
            service.setCommissionFee(new BigDecimal(request.commissionFee));
            service.setDiscountPercentage(new BigDecimal(request.discountPercentage));
            service.setStatus(request.status);
            ServicePackage savedCategory = serviceRepository.save(service);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteService(Long id) {
        if (serviceRepository.existsByPackageId(id)) {
            serviceRepository.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Service not found", BAD_REQUEST);
    }

    //extra stuff
    public ResponseEntity<List<ServicePackageResponse>> getAllServiceExtras() {
        return new ResponseEntity<>(serviceRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<ServicePackageResponse> getServiceByIdExtra(Long id) {
        try {
            return new ResponseEntity<>(mapToResponse(serviceRepository.findByPackageId(id).get()), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ServicePackageResponse(), BAD_REQUEST);
    }

    private ServicePackageResponse mapToResponse(ServicePackage servicePackage) {
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                servicePackage.getDiscountPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        );
        BigDecimal total = servicePackage.getPrice().multiply(discountMultiplier);
        BigDecimal commissionShare = total.multiply(servicePackage.getCommissionFee());
        BigDecimal expertShare = total.subtract(commissionShare);

        return new ServicePackageResponse(
                servicePackage.getPackageId(),
                servicePackage.getName(),
                servicePackage.getDescription(),
                servicePackage.getSessionCount(),
                servicePackage.getValidityDays(),
                servicePackage.getPrice(),
                servicePackage.getCommissionFee(),
                servicePackage.getDiscountPercentage(),
                servicePackage.getRequirements(),
                servicePackage.getStatus(),
                total,
                commissionShare,
                expertShare
        );
    }
    public String createURLPayment(ServicePackage servicePackage) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);
        String packageId = UUID.randomUUID().toString().substring(0, 6);

        String tmnCode = "FIWY31Q4";
        String secretKey = "4APPTF6B5SX37MHP9XJOS8FA5ZZ71GSE";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnURL = "http://localhost:8080/";

        String currCode = "VND";
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", packageId);
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + packageId);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount", servicePackage.getPrice().multiply(BigDecimal.valueOf(100)).toBigInteger().toString() + "00");
        vnpParams.put("vnp_ReturnUrl", returnURL);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "167.99.74.201");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'
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
}

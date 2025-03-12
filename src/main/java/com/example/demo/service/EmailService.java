package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String emailSender;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendEmail(String recipient, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailSender);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendResetPasswordEmail(String recipient, String token){
        String subject = "[PCS] - Reset Password Request";

        String resetLink = "http://localhost:8080/api/resetPassword/" + token;
        String body = "Hi " + userRepository.findByEmail(recipient).get().getFullName() + ",\n\n" +
                "We received a request to reset your password.\n\n" +
                "Please click the link below to reset your password:\n" +
                resetLink + "\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Best regards,\nYour Support Team";

        sendEmail(recipient, subject, body);
    }
}


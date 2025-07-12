package com.MyClassroom.ProductionLevelApplication.Services.EmailService;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserEmailService {

    @Autowired
    JavaMailSender mailSender;
    public void sendVerificationMail(String to, String token) {

        String subject = "Welcome to MyClassroom – Verify Your Email to Get Started!";
        String verificationLink = "http://localhost:8080/user/verify?token=" + token;
        String body = "Hi there,\n\n" +
                "Thank you for registering with MyClassroom!\n\n" +
                "To activate your account and start exploring, please verify your email by clicking the link below:\n\n" +
                "👉 Verify Now: " + verificationLink + "\n\n" +
                "If you didn’t sign up for this account, please ignore this email.\n\n" +
                "Welcome aboard,\n" +
                "— The MyClassroom Team";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("✅ Email sent successfully to: " + to);

        } catch (Exception e) {
            System.err.println("❌ Error sending email: " + e.getMessage());
        }

    }
}

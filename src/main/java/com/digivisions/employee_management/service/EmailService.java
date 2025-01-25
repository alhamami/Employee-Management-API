package com.digivisions.employee_management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private RateLimitingService rateLimitingService;

    @Async
    public void sendEmail(String email, String name) {

        logger.info("EmailService: sending email to "+email);

        if (rateLimitingService.Consume() == false) {
            logger.warn("Rate limit exceeded for email call");
            throw new RuntimeException("Rate limit exceeded, please try again");
        }

        String subject = "Welcome to our company";
        String body = "Congratulations "+name+"! You’re now a fully-fledged employee. We hope you’re excited about joining us.";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        logger.info("Successfully sent email to "+email);

        mailSender.send(mailMessage);
    }
}

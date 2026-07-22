package com.course_management_system.admin.serviceImpl;

import com.course_management_system.admin.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.mail.from:${SPRING_MAIL_USERNAME:}}")
    private String from;

    @Value("${spring.mail.username:${SPRING_MAIL_USERNAME:}}")
    private String username;

    public void sendOtp(String to, String otp) {
        if (!StringUtils.hasText(username)) {
            throw new BadRequestException("Email sender is not configured. Set SPRING_MAIL_USERNAME and SPRING_MAIL_PASSWORD.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(StringUtils.hasText(from) ? from : username);
        message.setTo(to);
        message.setSubject("Admin CMS verification code");
        message.setText("Your admin verification code is: " + otp + "\nThis code expires in 5 minutes.");

        try {
            mailSender.send(message);
        } catch (MailException exception) {
            throw new BadRequestException("Could not send OTP email: " + exception.getMessage());
        }
    }
}

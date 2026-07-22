package com.course_management_system.admin.serviceImpl;

import com.course_management_system.admin.dto.AuthRequest;
import com.course_management_system.admin.dto.AuthResponse;
import com.course_management_system.admin.dto.OtpRequest;
import com.course_management_system.admin.dto.OtpVerifyRequest;
import com.course_management_system.admin.dto.SignupRequest;
import com.course_management_system.admin.entity.AppUser;
import com.course_management_system.admin.exceptions.BadRequestException;
import com.course_management_system.admin.exceptions.ResourceNotFoundException;
import com.course_management_system.admin.repository.AppUserRepository;
import com.course_management_system.admin.security.JwtService;
import com.course_management_system.admin.security.TokenBlacklistService;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();

    public void signup(SignupRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }
        AppUser user = new AppUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN");
        user.setVerified(false);
        appUserRepository.save(user);
        sendOtp(request.getEmail());
    }

    public AuthResponse login(AuthRequest request) {
        AppUser user = findUser(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }
        if (!user.isVerified()) {
            throw new BadRequestException("Email is not verified. Please verify your email before login.");
        }
        return buildAuthResponse(user);
    }

    public void sendOtp(String email) {
        AppUser user = findUser(email);
        String otp = String.format("%06d", secureRandom.nextInt(1_000_000));
        user.setOtpCode(passwordEncoder.encode(otp));
        user.setOtpExpiresAt(LocalDateTime.now().plusMinutes(5));
        appUserRepository.save(user);
        emailService.sendOtp(email, otp);
    }

    public void sendOtp(OtpRequest request) {
        sendOtp(request.getEmail());
    }

    public void verifyOtp(OtpVerifyRequest request) {
        AppUser user = findUser(request.getEmail());
        if (user.getOtpCode() == null || user.getOtpExpiresAt() == null) {
            throw new BadRequestException("No OTP was requested");
        }
        if (LocalDateTime.now().isAfter(user.getOtpExpiresAt())) {
            throw new BadRequestException("OTP has expired");
        }
        if (!passwordEncoder.matches(request.getOtp(), user.getOtpCode())) {
            throw new BadRequestException("Invalid OTP");
        }
        user.setVerified(true);
        user.setOtpCode(null);
        user.setOtpExpiresAt(null);
        appUserRepository.save(user);
    }

    public void logout(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            tokenBlacklistService.blacklist(authorizationHeader.substring(7));
        }
    }

    private AuthResponse buildAuthResponse(AppUser user) {
        return new AuthResponse(jwtService.generateToken(user.getEmail(), user.getRole()), "Bearer", user.getEmail(),
                user.getRole());
    }

    private AppUser findUser(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
    }
}

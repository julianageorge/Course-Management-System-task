package com.course_management_system.publicservice.controller;

import com.course_management_system.publicservice.dto.AuthRequest;
import com.course_management_system.publicservice.dto.AuthResponse;
import com.course_management_system.publicservice.dto.OtpRequest;
import com.course_management_system.publicservice.dto.OtpVerifyRequest;
import com.course_management_system.publicservice.dto.SignupRequest;
import com.course_management_system.publicservice.serviceImpl.AuthService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Signup successful. OTP sent to email."));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/send-otp")
    public Map<String, String> sendOtp(@RequestBody OtpRequest request) {
        authService.sendOtp(request);
        return Map.of("message", "OTP sent to email.");
    }

    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(@RequestBody OtpVerifyRequest request) {
        authService.verifyOtp(request);
        return Map.of("message", "Email verified successfully. You can login now.");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        authService.logout(authorization);
        return ResponseEntity.noContent().build();
    }
}

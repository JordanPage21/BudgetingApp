package com.budgetingapp.controller;

import com.budgetingapp.dto.request.LoginRequest;
import com.budgetingapp.dto.request.UserRequest;
import com.budgetingapp.dto.response.AuthResponse;
import com.budgetingapp.dto.response.JwtAuthResponse;
import com.budgetingapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequest userRequest) {
        log.info("Register request received: {}", userRequest);
        JwtAuthResponse response = authService.register(userRequest);
        return ResponseEntity.ok(AuthResponse.success("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login request received: {}", loginRequest);
        JwtAuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(AuthResponse.success("Login successful", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout() {
        log.info("Logout request received");
        authService.logout();
        return ResponseEntity.ok(AuthResponse.success("Logout successful"));
    }
} 
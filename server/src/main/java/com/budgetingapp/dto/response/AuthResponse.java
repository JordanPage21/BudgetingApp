package com.budgetingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private boolean success;
    private String message;
    private JwtAuthResponse data;

    public static AuthResponse success(String message, JwtAuthResponse data) {
        return new AuthResponse(true, message, data);
    }

    public static AuthResponse success(String message) {
        return new AuthResponse(true, message, null);
    }

    public static AuthResponse error(String message) {
        return new AuthResponse(false, message, null);
    }
} 
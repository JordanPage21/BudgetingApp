package com.budgetingapp.service;

import com.budgetingapp.dto.request.LoginRequest;
import com.budgetingapp.dto.request.UserRequest;
import com.budgetingapp.dto.response.JwtAuthResponse;
import com.budgetingapp.model.User;

public interface AuthService {
    JwtAuthResponse login(LoginRequest loginRequest);
    User register(UserRequest userRequest);
    void logout();
} 
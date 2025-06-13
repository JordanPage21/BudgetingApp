package com.budgetingapp.service.impl;

import com.budgetingapp.dto.request.LoginRequest;
import com.budgetingapp.dto.request.UserRequest;
import com.budgetingapp.dto.response.JwtAuthResponse;
import com.budgetingapp.exception.ResourceAlreadyExistsException;
import com.budgetingapp.model.User;
import com.budgetingapp.repository.UserRepository;
import com.budgetingapp.security.JwtTokenProvider;
import com.budgetingapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthResponse(jwt);
    }

    @Override
    @Transactional
    public User register(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ResourceAlreadyExistsException("Username is already taken");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Email is already registered");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
} 
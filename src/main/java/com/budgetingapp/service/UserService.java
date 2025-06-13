package com.budgetingapp.service;

import com.budgetingapp.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 
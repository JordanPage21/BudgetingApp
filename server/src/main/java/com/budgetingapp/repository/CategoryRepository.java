package com.budgetingapp.repository;

import com.budgetingapp.model.Category;
import com.budgetingapp.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    boolean existsByName(String name);
    List<Category> findByUser(User user);
    Category findByUserAndName(User user, String name);
    boolean existsByUserAndName(User user, String name);
} 
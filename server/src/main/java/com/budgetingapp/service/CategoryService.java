package com.budgetingapp.service;

import com.budgetingapp.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
    Optional<Category> getCategory(Long id);
    Optional<Category> getCategoryByName(String name);
    List<Category> getAllCategories();
    boolean existsByName(String name);
    
    // User-specific methods
    List<Category> getUserCategories(String username);
    Optional<Category> getUserCategoryByName(String username, String name);
    boolean existsByNameForUser(String username, String name);
    void deleteUserCategory(String username, Long categoryId);
} 
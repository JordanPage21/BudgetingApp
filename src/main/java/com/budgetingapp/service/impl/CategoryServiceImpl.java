package com.budgetingapp.service.impl;

import com.budgetingapp.model.Category;
import com.budgetingapp.model.User;
import com.budgetingapp.repository.CategoryRepository;
import com.budgetingapp.service.CategoryService;
import com.budgetingapp.service.UserService;
import com.budgetingapp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public List<Category> getUserCategories(String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return categoryRepository.findByUser(user);
    }

    @Override
    public Optional<Category> getUserCategoryByName(String username, String name) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return Optional.ofNullable(categoryRepository.findByUserAndName(user, name));
    }

    @Override
    public boolean existsByNameForUser(String username, String name) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return categoryRepository.existsByUserAndName(user, name);
    }

    @Override
    @Transactional
    public void deleteUserCategory(String username, Long categoryId) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        
        if (!category.getUser().equals(user)) {
            throw new ResourceNotFoundException("Category does not belong to user: " + username);
        }
        
        categoryRepository.delete(category);
    }
} 
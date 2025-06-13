package com.budgetingapp.controller;

import com.budgetingapp.dto.request.CategoryRequest;
import com.budgetingapp.dto.response.ApiResponse;
import com.budgetingapp.model.Category;
import com.budgetingapp.model.User;
import com.budgetingapp.service.CategoryService;
import com.budgetingapp.service.UserService;
import com.budgetingapp.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/{username}")
    public ResponseEntity<ApiResponse<Category>> createCategory(
            @PathVariable String username,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Creating category for user: {}", username);
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        
        if (categoryService.existsByNameForUser(username, categoryRequest.getName())) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Category with name '" + categoryRequest.getName() + "' already exists for this user"));
        }
        
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setUser(user);
        
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(ApiResponse.success(createdCategory));
    }

    @PutMapping("/{username}/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable String username,
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        
        Category existingCategory = categoryService.getCategory(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        if (!existingCategory.getUser().equals(user)) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Category does not belong to user: " + username));
        }
        
        if (!existingCategory.getName().equals(categoryRequest.getName()) && 
            categoryService.existsByNameForUser(username, categoryRequest.getName())) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Category with name '" + categoryRequest.getName() + "' already exists for this user"));
        }
        
        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());
        
        Category updatedCategory = categoryService.updateCategory(id, existingCategory);
        return ResponseEntity.ok(ApiResponse.success(updatedCategory));
    }

    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(
            @PathVariable String username,
            @PathVariable Long id) {
        categoryService.deleteUserCategory(username, id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully"));
    }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategory(
            @PathVariable String username,
            @PathVariable Long id) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        
        Category category = categoryService.getCategory(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        if (!category.getUser().equals(user)) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Category does not belong to user: " + username));
        }
        
        return ResponseEntity.ok(ApiResponse.success(category));
    }

    @GetMapping("/{username}/name/{name}")
    public ResponseEntity<ApiResponse<Category>> getCategoryByName(
            @PathVariable String username,
            @PathVariable String name) {
        Category category = categoryService.getUserCategoryByName(username, name)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name));
        return ResponseEntity.ok(ApiResponse.success(category));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(
            @PathVariable String username) {
        List<Category> categories = categoryService.getUserCategories(username);
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @GetMapping("/{username}/check-name/{name}")
    public ResponseEntity<ApiResponse<Boolean>> checkCategoryNameExists(
            @PathVariable String username,
            @PathVariable String name) {
        boolean exists = categoryService.existsByNameForUser(username, name);
        return ResponseEntity.ok(ApiResponse.success(exists));
    }
} 
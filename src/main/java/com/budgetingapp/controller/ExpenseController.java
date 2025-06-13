package com.budgetingapp.controller;

import com.budgetingapp.dto.request.ExpenseRequest;
import com.budgetingapp.dto.response.ApiResponse;
import com.budgetingapp.model.Expense;
import com.budgetingapp.model.User;
import com.budgetingapp.model.Category;
import com.budgetingapp.service.ExpenseService;
import com.budgetingapp.service.UserService;
import com.budgetingapp.service.CategoryService;
import com.budgetingapp.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, UserService userService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<ApiResponse<Expense>> createExpense(
            @PathVariable String username,
            @Valid @RequestBody ExpenseRequest expenseRequest) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Expense expense = expenseService.createExpense(user, expenseRequest);
        return ResponseEntity.ok(ApiResponse.success("Expense created successfully", expense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Expense>> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest expenseRequest) {
        Expense expense = expenseService.updateExpense(id, expenseRequest);
        return ResponseEntity.ok(ApiResponse.success("Expense updated successfully", expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok(ApiResponse.success("Expense deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Expense>> getExpense(@PathVariable Long id) {
        Expense expense = expenseService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(expense));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<ApiResponse<List<Expense>>> getUserExpenses(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Expense> expenses = expenseService.findByUser(user);
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }

    @GetMapping("/user/{username}/date-range")
    public ResponseEntity<ApiResponse<List<Expense>>> getExpensesByDateRange(
            @PathVariable String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Expense> expenses = expenseService.findByUserAndDateBetween(user, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }

    @GetMapping("/user/{username}/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<Expense>>> getExpensesByCategory(
            @PathVariable String username,
            @PathVariable Long categoryId) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Category category = categoryService.getCategory(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        List<Expense> expenses = expenseService.findByUserAndCategory(user, category);
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }

    @GetMapping("/user/{username}/recurring")
    public ResponseEntity<ApiResponse<List<Expense>>> getRecurringExpenses(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Expense> expenses = expenseService.findRecurringExpenses(user);
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }

    @GetMapping("/user/{username}/search")
    public ResponseEntity<ApiResponse<List<Expense>>> searchExpenses(
            @PathVariable String username,
            @RequestParam String name) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Expense> expenses = expenseService.findByUserAndNameContaining(user, name);
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }
} 
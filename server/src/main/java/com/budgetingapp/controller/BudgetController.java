package com.budgetingapp.controller;

import com.budgetingapp.dto.request.BudgetRequest;
import com.budgetingapp.dto.response.ApiResponse;
import com.budgetingapp.model.Budget;
import com.budgetingapp.model.User;
import com.budgetingapp.service.BudgetService;
import com.budgetingapp.service.UserService;
import com.budgetingapp.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    @Autowired
    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<ApiResponse<Budget>> createBudget(
            @PathVariable String username,
            @Valid @RequestBody BudgetRequest budgetRequest) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Budget budget = budgetService.createBudget(user, budgetRequest);
        return ResponseEntity.ok(ApiResponse.success("Budget created successfully", budget));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Budget>> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest budgetRequest) {
        Budget budget = budgetService.updateBudget(id, budgetRequest);
        return ResponseEntity.ok(ApiResponse.success("Budget updated successfully", budget));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok(ApiResponse.success("Budget deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Budget>> getBudget(@PathVariable Long id) {
        Budget budget = budgetService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(budget));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<ApiResponse<List<Budget>>> getUserBudgets(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Budget> budgets = budgetService.findByUser(user);
        if (budgets.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("No budgets found for user", budgets));
        }
        return ResponseEntity.ok(ApiResponse.success(budgets));
    }

    @GetMapping("/user/{username}/month/{yearMonth}")
    public ResponseEntity<ApiResponse<Budget>> getBudgetByMonth(
            @PathVariable String username,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Optional<Budget> budget = budgetService.findByUserAndMonth(user, yearMonth);
        if (budget.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(
                String.format("No budget found for %s", yearMonth.format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy"))),
                null
            ));
        }
        return ResponseEntity.ok(ApiResponse.success(budget.get()));
    }

    @GetMapping("/user/{username}/chronological")
    public ResponseEntity<ApiResponse<List<Budget>>> getBudgetsChronologically(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Budget> budgets = budgetService.findByUserOrderByMonthDesc(user);
        if (budgets.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("No budgets found for user", budgets));
        }
        return ResponseEntity.ok(ApiResponse.success(budgets));
    }
} 
package com.budgetingapp.controller;

import com.budgetingapp.dto.request.IncomeRequest;
import com.budgetingapp.dto.response.ApiResponse;
import com.budgetingapp.model.Income;
import com.budgetingapp.model.User;
import com.budgetingapp.service.IncomeService;
import com.budgetingapp.service.UserService;
import com.budgetingapp.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private final UserService userService;

    @Autowired
    public IncomeController(IncomeService incomeService, UserService userService) {
        this.incomeService = incomeService;
        this.userService = userService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<ApiResponse<Income>> createIncome(
            @PathVariable String username,
            @Valid @RequestBody IncomeRequest incomeRequest) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Income income = incomeService.createIncome(user, incomeRequest);
        return ResponseEntity.ok(ApiResponse.success("Income created successfully", income));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Income>> updateIncome(
            @PathVariable Long id,
            @Valid @RequestBody IncomeRequest incomeRequest) {
        Income income = incomeService.updateIncome(id, incomeRequest);
        return ResponseEntity.ok(ApiResponse.success("Income updated successfully", income));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.ok(ApiResponse.success("Income deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Income>> getIncome(@PathVariable Long id) {
        Income income = incomeService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(income));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<List<Income>>> getUserIncomes(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Income> incomes = incomeService.findByUser(user);
        return ResponseEntity.ok(ApiResponse.success(incomes));
    }

    @GetMapping("/{username}/date-range")
    public ResponseEntity<ApiResponse<List<Income>>> getIncomesByDateRange(
            @PathVariable String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Income> incomes = incomeService.findByUserAndDateBetween(user, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(incomes));
    }

    @GetMapping("/{username}/recurring")
    public ResponseEntity<ApiResponse<List<Income>>> getRecurringIncomes(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Income> incomes = incomeService.findRecurringIncomes(user);
        return ResponseEntity.ok(ApiResponse.success(incomes));
    }
} 
package com.budgetingapp.service;

import com.budgetingapp.dto.request.BudgetRequest;
import com.budgetingapp.model.Budget;
import com.budgetingapp.model.User;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetService {
    Budget createBudget(User user, BudgetRequest budgetRequest);
    Budget updateBudget(Long id, BudgetRequest budgetRequest);
    void deleteBudget(Long id);
    Budget findById(Long id);
    List<Budget> findByUser(User user);
    Optional<Budget> findByUserAndMonth(User user, YearMonth month);
    List<Budget> findByUserOrderByMonthDesc(User user);
} 
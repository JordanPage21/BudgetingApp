package com.budgetingapp.service;

import com.budgetingapp.dto.request.BudgetRequest;
import com.budgetingapp.model.Budget;
import com.budgetingapp.model.User;
import java.time.YearMonth;
import java.util.List;

public interface BudgetService {
    Budget createBudget(User user, BudgetRequest budgetRequest);
    Budget updateBudget(Long id, BudgetRequest budgetRequest);
    void deleteBudget(Long id);
    Budget findById(Long id);
    List<Budget> findByUser(User user);
    Budget findByUserAndMonth(User user, YearMonth month);
    List<Budget> findByUserOrderByMonthDesc(User user);
} 
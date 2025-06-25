package com.budgetingapp.service;

import com.budgetingapp.dto.request.ExpenseRequest;
import com.budgetingapp.model.Expense;
import com.budgetingapp.model.User;
import com.budgetingapp.model.Category;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    Expense createExpense(User user, ExpenseRequest expenseRequest);
    Expense updateExpense(Long id, ExpenseRequest expenseRequest);
    void deleteExpense(Long id);
    Expense findById(Long id);
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserAndCategory(User user, Category category);
    List<Expense> findRecurringExpenses(User user);
    List<Expense> findByUserAndNameContaining(User user, String name);
} 
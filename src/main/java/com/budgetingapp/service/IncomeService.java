package com.budgetingapp.service;

import com.budgetingapp.dto.request.IncomeRequest;
import com.budgetingapp.model.Income;
import com.budgetingapp.model.User;
import java.time.LocalDate;
import java.util.List;

public interface IncomeService {
    Income createIncome(User user, IncomeRequest incomeRequest);
    Income updateIncome(Long id, IncomeRequest incomeRequest);
    void deleteIncome(Long id);
    Income findById(Long id);
    List<Income> findByUser(User user);
    List<Income> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Income> findRecurringIncomes(User user);
} 
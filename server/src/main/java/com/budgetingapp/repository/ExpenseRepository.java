package com.budgetingapp.repository;

import com.budgetingapp.model.Expense;
import com.budgetingapp.model.User;
import com.budgetingapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserAndCategory(User user, Category category);
    List<Expense> findByUserAndIsRecurringTrue(User user);
    List<Expense> findByUserAndNameContaining(User user, String name);
} 
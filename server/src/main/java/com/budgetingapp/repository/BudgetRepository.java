package com.budgetingapp.repository;

import com.budgetingapp.model.Budget;
import com.budgetingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.YearMonth;
import java.util.Optional;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);
    Optional<Budget> findByUserAndMonth(User user, YearMonth month);
    List<Budget> findByUserOrderByMonthDesc(User user);
} 
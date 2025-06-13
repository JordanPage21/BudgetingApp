package com.budgetingapp.repository;

import com.budgetingapp.model.Income;
import com.budgetingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);
    List<Income> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Income> findByUserAndIsRecurringTrue(User user);
    List<Income> findByUserAndSource(User user, String source);
} 
package com.budgetingapp.service.impl;

import com.budgetingapp.dto.request.BudgetRequest;
import com.budgetingapp.model.Budget;
import com.budgetingapp.model.User;
import com.budgetingapp.repository.BudgetRepository;
import com.budgetingapp.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget createBudget(User user, BudgetRequest budgetRequest) {
        // Check if budget already exists for the month
        if (budgetRepository.findByUserAndMonth(user, budgetRequest.getMonth()).isPresent()) {
            throw new RuntimeException("Budget already exists for month: " + budgetRequest.getMonth());
        }

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setMonth(budgetRequest.getMonth());
        budget.setTotalIncome(budgetRequest.getTotalIncome());
        budget.setTotalExpenses(budgetRequest.getTotalExpenses());
        budget.setSavingsGoal(budgetRequest.getSavingsGoal());
        budget.setNotes(budgetRequest.getNotes());

        return budgetRepository.save(budget);
    }

    @Override
    public Budget updateBudget(Long id, BudgetRequest budgetRequest) {
        Budget budget = findById(id);
        
        // Check if updating month would conflict with another budget
        if (!budget.getMonth().equals(budgetRequest.getMonth())) {
            budgetRepository.findByUserAndMonth(budget.getUser(), budgetRequest.getMonth())
                    .ifPresent(existingBudget -> {
                        if (!existingBudget.getId().equals(id)) {
                            throw new RuntimeException("Budget already exists for month: " + budgetRequest.getMonth());
                        }
                    });
        }

        budget.setMonth(budgetRequest.getMonth());
        budget.setTotalIncome(budgetRequest.getTotalIncome());
        budget.setTotalExpenses(budgetRequest.getTotalExpenses());
        budget.setSavingsGoal(budgetRequest.getSavingsGoal());
        budget.setNotes(budgetRequest.getNotes());

        return budgetRepository.save(budget);
    }

    @Override
    public void deleteBudget(Long id) {
        Budget budget = findById(id);
        budgetRepository.delete(budget);
    }

    @Override
    @Transactional(readOnly = true)
    public Budget findById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Budget> findByUser(User user) {
        return budgetRepository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Budget> findByUserAndMonth(User user, YearMonth month) {
        return budgetRepository.findByUserAndMonth(user, month);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Budget> findByUserOrderByMonthDesc(User user) {
        return budgetRepository.findByUserOrderByMonthDesc(user);
    }
} 
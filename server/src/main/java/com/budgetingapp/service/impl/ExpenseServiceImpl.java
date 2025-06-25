package com.budgetingapp.service.impl;

import com.budgetingapp.dto.request.ExpenseRequest;
import com.budgetingapp.model.Expense;
import com.budgetingapp.model.User;
import com.budgetingapp.model.Category;
import com.budgetingapp.repository.ExpenseRepository;
import com.budgetingapp.repository.CategoryRepository;
import com.budgetingapp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Expense createExpense(User user, ExpenseRequest expenseRequest) {
        Category category = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + expenseRequest.getCategoryId()));

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(category);
        expense.setName(expenseRequest.getName());
        expense.setAmount(expenseRequest.getAmount());
        expense.setDate(expenseRequest.getDate());
        expense.setDescription(expenseRequest.getDescription());
        expense.setRecurring(expenseRequest.isRecurring());
        expense.setFrequency(expenseRequest.getFrequency());

        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, ExpenseRequest expenseRequest) {
        Expense expense = findById(id);
        Category category = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + expenseRequest.getCategoryId()));

        expense.setCategory(category);
        expense.setName(expenseRequest.getName());
        expense.setAmount(expenseRequest.getAmount());
        expense.setDate(expenseRequest.getDate());
        expense.setDescription(expenseRequest.getDescription());
        expense.setRecurring(expenseRequest.isRecurring());
        expense.setFrequency(expenseRequest.getFrequency());

        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = findById(id);
        expenseRepository.delete(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public Expense findById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findByUserAndCategory(User user, Category category) {
        return expenseRepository.findByUserAndCategory(user, category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findRecurringExpenses(User user) {
        return expenseRepository.findByUserAndIsRecurringTrue(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findByUserAndNameContaining(User user, String name) {
        return expenseRepository.findByUserAndNameContaining(user, name);
    }
} 
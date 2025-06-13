package com.budgetingapp.service.impl;

import com.budgetingapp.dto.request.IncomeRequest;
import com.budgetingapp.model.Income;
import com.budgetingapp.model.User;
import com.budgetingapp.repository.IncomeRepository;
import com.budgetingapp.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Income createIncome(User user, IncomeRequest incomeRequest) {
        Income income = new Income();
        income.setUser(user);
        income.setSource(incomeRequest.getSource());
        income.setAmount(incomeRequest.getAmount());
        income.setDate(incomeRequest.getDate());
        income.setDescription(incomeRequest.getDescription());
        income.setRecurring(incomeRequest.isRecurring());
        income.setFrequency(incomeRequest.getFrequency());

        return incomeRepository.save(income);
    }

    @Override
    public Income updateIncome(Long id, IncomeRequest incomeRequest) {
        Income income = findById(id);
        
        income.setSource(incomeRequest.getSource());
        income.setAmount(incomeRequest.getAmount());
        income.setDate(incomeRequest.getDate());
        income.setDescription(incomeRequest.getDescription());
        income.setRecurring(incomeRequest.isRecurring());
        income.setFrequency(incomeRequest.getFrequency());

        return incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(Long id) {
        Income income = findById(id);
        incomeRepository.delete(income);
    }

    @Override
    @Transactional(readOnly = true)
    public Income findById(Long id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Income> findByUser(User user) {
        return incomeRepository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Income> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate) {
        return incomeRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Income> findRecurringIncomes(User user) {
        return incomeRepository.findByUserAndIsRecurringTrue(user);
    }
} 
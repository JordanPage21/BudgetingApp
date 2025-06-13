package com.budgetingapp.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import java.time.YearMonth;

@Data
@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(name = "budget_month", nullable = false)
    private YearMonth month;

    @Column(name = "total_income", nullable = false)
    private BigDecimal totalIncome;

    @Column(name = "total_expenses", nullable = false)
    private BigDecimal totalExpenses;

    @Column(name = "savings_goal", nullable = false)
    private BigDecimal savingsGoal;

    @Column
    private String notes;
} 
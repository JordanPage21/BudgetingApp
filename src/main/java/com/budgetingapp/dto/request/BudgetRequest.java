package com.budgetingapp.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.YearMonth;

@Data
public class BudgetRequest {
    @NotNull(message = "Month is required")
    private YearMonth month;

    @NotNull(message = "Total income is required")
    @Positive(message = "Total income must be positive")
    private BigDecimal totalIncome;

    @NotNull(message = "Total expenses is required")
    @Positive(message = "Total expenses must be positive")
    private BigDecimal totalExpenses;

    @NotNull(message = "Savings goal is required")
    @Positive(message = "Savings goal must be positive")
    private BigDecimal savingsGoal;

    private String notes;
} 
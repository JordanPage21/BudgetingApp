package com.budgetingapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private String description;

    @NotNull(message = "Recurring status is required")
    private boolean isRecurring;

    private String frequency;
} 
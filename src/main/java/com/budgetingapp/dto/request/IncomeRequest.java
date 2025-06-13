package com.budgetingapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IncomeRequest {
    @NotBlank(message = "Source is required")
    private String source;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private String description;

    @NotNull(message = "Recurring status is required")
    @JsonProperty("isRecurring")
    private boolean isRecurring;

    private String frequency;
} 
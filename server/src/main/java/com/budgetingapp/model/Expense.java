package com.budgetingapp.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean isRecurring;

    @Column
    private String frequency; // Monthly, Bi-weekly, Weekly, etc.
} 
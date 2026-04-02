package com.money.manager.backend.model;

import com.money.manager.backend.model.enums.DivisionType;
import com.money.manager.backend.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private double amount;

    private String category;          // ✅ STRING

    @Enumerated(EnumType.STRING)
    private DivisionType division;
    private String description;
    private LocalDateTime transactionDateTime;
    private String accountId;
    private LocalDateTime createdAt;
}

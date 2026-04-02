package com.money.manager.backend.dto;

import com.money.manager.backend.model.enums.DivisionType;
import com.money.manager.backend.model.enums.TransactionType;

import java.time.LocalDateTime;

public class TransactionRequestDto {

    private TransactionType type;
    private double amount;
    private String category;              // ✅ STRING
    private DivisionType division;
    private String description;
    private LocalDateTime transactionDateTime;
    private String accountId;

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public DivisionType getDivision() { return division; }
    public void setDivision(DivisionType division) { this.division = division; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getTransactionDateTime() { return transactionDateTime; }
    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
}

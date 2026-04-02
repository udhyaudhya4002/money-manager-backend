package com.money.manager.backend.service;

import com.money.manager.backend.dto.TransactionRequestDto;
import com.money.manager.backend.model.Account;
import com.money.manager.backend.model.Transaction;
import com.money.manager.backend.model.enums.TransactionType;
import com.money.manager.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    // ✅ ADD TRANSACTION (SAFE)
    public Transaction addTransaction(TransactionRequestDto dto) {

        if (dto.getAccountId() == null || dto.getAccountId().isBlank()) {
            throw new RuntimeException("Account ID not provided");
        }

        Account account = accountService.getAccountById(dto.getAccountId());

        if (dto.getType() == TransactionType.EXPENSE &&
                dto.getAmount() > account.getBalance()) {
            throw new RuntimeException("Insufficient balance");
        }

        if (dto.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance() + dto.getAmount());
        } else {
            account.setBalance(account.getBalance() - dto.getAmount());
        }

        accountService.saveAccount(account);

        Transaction transaction = new Transaction();
        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setCategory(
                dto.getCategory() != null ? dto.getCategory() : "OTHER"
        );
        transaction.setDivision(dto.getDivision());
        transaction.setDescription(dto.getDescription());
        transaction.setTransactionDateTime(dto.getTransactionDateTime());
        transaction.setAccountId(dto.getAccountId());
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    // EDIT WITHIN 12 HOURS
    public Transaction editTransaction(String id, TransactionRequestDto dto) {

        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        long hours = Duration.between(existing.getCreatedAt(), LocalDateTime.now()).toHours();
        if (hours > 12) {
            throw new RuntimeException("Edit not allowed after 12 hours");
        }

        existing.setCategory(dto.getCategory());
        existing.setDivision(dto.getDivision());
        existing.setDescription(dto.getDescription());

        return transactionRepository.save(existing);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Map<String, Double> getCategorySummary() {
        List<Transaction> transactions = transactionRepository.findAll();
        Map<String, Double> summary = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.EXPENSE) {
                String category = t.getCategory();
                summary.put(category,
                        summary.getOrDefault(category, 0.0) + t.getAmount());
            }
        }
        return summary;
    }

    public List<Transaction> getTransactionsBetweenDates(
            LocalDateTime start,
            LocalDateTime end) {
        return transactionRepository.findByTransactionDateTimeBetween(start, end);
    }
    public void deleteTransaction(String id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Account account = accountService.getAccountById(transaction.getAccountId());

        // Rollback balance
        if (transaction.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance() - transaction.getAmount());
        } else {
            account.setBalance(account.getBalance() + transaction.getAmount());
        }

        accountService.saveAccount(account);
        transactionRepository.deleteById(id);
    }

}


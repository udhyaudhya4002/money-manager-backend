package com.money.manager.backend.controller;

import com.money.manager.backend.dto.TransactionRequestDto;
import com.money.manager.backend.model.Transaction;
import com.money.manager.backend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // ✅ ADD INCOME / EXPENSE
    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequestDto dto) {
        try {
            Transaction saved = transactionService.addTransaction(dto);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ EDIT TRANSACTION (12 HOURS RULE)
    @PutMapping("/{id}")
    public ResponseEntity<?> editTransaction(
            @PathVariable String id,
            @RequestBody TransactionRequestDto dto
    ) {
        try {
            Transaction updated = transactionService.editTransaction(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ GET ALL TRANSACTIONS (HISTORY)
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // ✅ FILTER BY DATE RANGE (FIXED)
    @GetMapping("/filter")
    public List<Transaction> filterByDate(
            @RequestParam String start,
            @RequestParam String end
    ) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return transactionService.getTransactionsBetweenDates(startDate, endDate);
    }

    // ✅ CATEGORY SUMMARY
    @GetMapping("/summary/categories")
    public Map<String, Double> categorySummary() {
        return transactionService.getCategorySummary();
    }
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
    }

}

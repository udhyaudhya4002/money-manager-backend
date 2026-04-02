package com.money.manager.backend.repository;

import com.money.manager.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByTransactionDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}

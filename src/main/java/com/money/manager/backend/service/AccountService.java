package com.money.manager.backend.service;

import com.money.manager.backend.model.Account;
import com.money.manager.backend.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void initializeAccount(String name, double balance) {
        Account account = new Account();
        account.setName(name);
        account.setBalance(balance);
        account.setCreatedAt(LocalDateTime.now());
        accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

}

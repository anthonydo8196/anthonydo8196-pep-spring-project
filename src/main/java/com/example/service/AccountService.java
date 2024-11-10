package com.example.service;

import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;

// Service class contains business logic of the application. It's responsible for processing data received
// from the controller. An intermediary between controller and repository.

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public boolean findByUsername(String username) {
        return accountRepository.findByUsername(username) != null;
    }

    public Account loginAccount(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}

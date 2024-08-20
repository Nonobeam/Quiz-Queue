package com.example.account.service;

import com.example.account.core.Account;
import com.example.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account getAccountByPhone(String phone) {
        return accountRepository.findByPhone(phone).orElseThrow();
    }
}

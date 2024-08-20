package com.example.account.core;

import com.example.account.service.AccountService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AccountDTO {
    private String name;
    private String phone;
    private LocalDate birthday;

    private static final Logger log = LoggerFactory.getLogger(AccountDTO.class);
    private final AccountService accountService;

    public AccountDTO deserialize(Account account) {
        return AccountDTO.builder()
                .name(account.getName())
                .phone(account.getPhone())
                .birthday(account.getBirthday())
                .build();
    }

    public Account serialize(AccountDTO accountDTO) {
        Account account = null;
        try {
            account = accountService.getAccountByPhone(accountDTO.getPhone());
        } catch (Exception e) {
            log.error("Error while serialize account: {}", e.getMessage());
        }

        return account;
    }
}

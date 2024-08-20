package com.example.account.core;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("Account")
public class Account {
    private String id;
    private String email;
    private String password;
    private Role role;
    private String name;
    private String phone;
    private LocalDate birthday;
    private boolean subscription;
    private boolean active;
}

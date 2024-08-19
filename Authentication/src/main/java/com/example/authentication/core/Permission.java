package com.example.authentication.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    READ("read"),
    WRITE("write"),
    DELETE("delete"),
    UPDATE("update")
    ;

    private final String permissions;
}
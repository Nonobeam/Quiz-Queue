package com.example.authentication.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.authentication.core.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    STUDENT(
            Set.of(
                    READ,
                    WRITE
            )
    ),
    TEACHER(
            Set.of(
                    READ,
                    WRITE
            )
    ),
    ADMIN(
            Set.of(
                    READ,
                    WRITE,
                    UPDATE,
                    DELETE
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

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
    CUSTOMER(
            Set.of(
                    READ,
                    WRITE
            )
    ),
    DENTIST(
            Set.of(
                    READ,
                    WRITE
            )
    ),
    STAFF(
            Set.of(
                    READ,
                    WRITE,
                    UPDATE,
                    DELETE
            )
    ),
    MANAGER(
            Set.of(
                    READ,
                    WRITE,
                    UPDATE,
                    DELETE
            )
    ),
    ADMIN(
            Set.of(
                    READ,
                    WRITE,
                    DELETE,
                    UPDATE
            )
    ),
    BOSS(
            Set.of(
                    READ,
                    WRITE,
                    DELETE,
                    UPDATE
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

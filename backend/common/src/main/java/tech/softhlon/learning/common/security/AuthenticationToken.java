// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.common.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Getter
public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String name;
    private final UUID accountId;

    public AuthenticationToken(
          String name,
          String accountId,
          Collection<? extends GrantedAuthority> authorities) {

        super(name, authorities);
        this.name = name;
        this.accountId = UUID.fromString(accountId);

    }

    public UUID getAccountId() {

        return accountId;

    }

    @Override
    public Object getCredentials() {

        return null;

    }

    @Override
    public Object getDetails() {

        return null;

    }

    @Override
    public Object getPrincipal() {

        return null;

    }

    @Override
    public boolean isAuthenticated() {

        return true;

    }

    @Override
    public void setAuthenticated(
          boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {

        return name;

    }
}

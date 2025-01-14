// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import tech.softhlon.learning.common.security.AuthenticationToken;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component
@RequiredArgsConstructor
class CustomAuthenticationManager implements AuthenticationManager {
    private final JwtProcessor jwtProcessor;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var token = authentication.getCredentials().toString();
        if (jwtProcessor.isTokenValid(token)) {
            var claims = jwtProcessor.getAllClaimsFromToken(token);
            var name = claims.get("name", String.class);
            var accountId = claims.get("accountId", String.class);

            return new AuthenticationToken(
                  name,
                  accountId,
                  List.of());
        } else {
            throw new InvalidTokenException();
        }
    }
}

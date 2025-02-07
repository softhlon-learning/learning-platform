// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import tech.javafullstack.accounts.domain.JwtService;
import tech.javafullstack.common.security.AuthenticationToken;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Spring authentication manager.
 */
@Component
@RequiredArgsConstructor
class CustomAuthenticationManager implements AuthenticationManager {

    private final JwtService jwtService;

    /**
     * Authenticate user.
     * @param authentication Authentication token
     * @return Authentication
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(
          Authentication authentication) throws AuthenticationException {

        var token = authentication
              .getCredentials()
              .toString();

        if (jwtService.isTokenValid(token)) {
            var claims = jwtService.getAllClaimsFromToken(token);

            var name = claims.get(
                  "name",
                  String.class
            );

            var accountId = claims.get(
                  "accountId",
                  String.class
            );

            return new AuthenticationToken(
                  name,
                  accountId,
                  List.of());

        } else {
            throw new InvalidTokenException();
        }

    }

}


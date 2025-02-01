// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.application;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckTokenResult.TokenExists;
import tech.softhlon.learning.accounts.domain.JwtService;
import tech.softhlon.learning.common.security.AuthenticationToken;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String ACCOUNT = "account";

    private final JwtService jwtService;
    private final CheckTokenRepository checkTokenRepository;

    @Override
    protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException {

        try {
            var token = jwtService.extractToken(
                  request);

            if (token != null && jwtService.isTokenValid(token)
                  && !isTokenInvalidated(token)) {

                var authentication = SecurityContextHolder
                      .getContext()
                      .getAuthentication();

                var claims = jwtService.getAllClaimsFromToken(
                      token);

                var authToken = new AuthenticationToken(
                      (String) claims.get("sub"),
                      (String) claims.get("accountId"),
                      List.of());

                authToken
                      .setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));

                SecurityContextHolder
                      .getContext()
                      .setAuthentication(authToken);

                setMDC(authToken.getName());

            } else {
                setMDC("anonymous");
            }
        } catch (Throwable cause) {

            log.error("", cause);
            setMDC("anonymous");

        }

        filterChain.doFilter(
              request,
              response);

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private boolean isTokenInvalidated(
          String token) {

        try {
            var tokenHash = jwtService.tokenHash(
                  token);

            var result = checkTokenRepository.execute(
                  tokenHash);

            if (result instanceof TokenExists) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            return false;
        }

        return false;

    }

    private void setMDC(
          String accountId) {

        MDC.put(ACCOUNT,
              accountId);

    }

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckTokenRequest;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckTokenResult.TokenExists;
import tech.softhlon.learning.accounts.domain.JwtService;
import tech.softhlon.learning.common.security.AuthenticationToken;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CheckTokenRepository checkTokenRepository;

    @Override
    protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException {

        try {
            var token = extractToken(request);
            if (token != null && jwtService.isTokenValid(token) && !isTokenInvalidated(token)) {
                var authentication = SecurityContextHolder.getContext().getAuthentication();
                var claims = jwtService.getAllClaimsFromToken(token);
                var authToken = new AuthenticationToken(
                      (String) claims.get("name"),
                      (String) claims.get("accountId"),
                      List.of());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Throwable cause) {
            log.error("", cause);
        }
        filterChain.doFilter(request, response);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private boolean isTokenInvalidated(String token) {
        try {
            var tokenHash = tokenHash(token);
            var result = checkTokenRepository.execute(new CheckTokenRequest(tokenHash));
            if (result instanceof TokenExists) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return false;
    }

    private String extractToken(HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String tokenHash(String token) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(token.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}

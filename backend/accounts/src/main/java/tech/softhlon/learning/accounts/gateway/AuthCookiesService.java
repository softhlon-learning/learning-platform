// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class AuthCookiesService {

    static final String AUTHORIZATION = "Authorization";
    private static final String AUTHENTICATED = "Authenticated";
    private final int maxAge;

    public AuthCookiesService(
          @Value("${jwt.expiration}") int maxAge) {

        this.maxAge = maxAge;

    }

    void addAuthSucceededCookies(
          HttpServletResponse response,
          String token) {

        addCookie(
              response,
              AUTHORIZATION,
              token,
              true,
              maxAge);

        addCookie(
              response,
              AUTHENTICATED,
              "true",
              false,
              maxAge);

    }

    void addAuthFailedCookies(
          HttpServletResponse response) {

        addCookie(
              response,
              AUTHORIZATION,
              null,
              true,
              0);

        addCookie(
              response,
              AUTHENTICATED,
              "false",
              false,
              Integer.MAX_VALUE);

    }

    void resetAuthCookies(
          HttpServletResponse response) {

        addCookie(
              response,
              AUTHORIZATION,
              null,
              true,
              0);

        addCookie(
              response,
              AUTHENTICATED,
              "false",
              false,
              Integer.MAX_VALUE);

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void addCookie(
          HttpServletResponse response,
          String name,
          String value,
          boolean httpOnly,
          int maxAge) {

        var cookie = new Cookie(
              name,
              value);

        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);

    }

}

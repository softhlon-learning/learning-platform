// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Authentication cookies service.
 */
@Service
class AuthCookiesService {

    static final String AUTHORIZATION = "Authorization";
    private static final String AUTHENTICATED = "Authenticated";
    private static final String SUBSCRIBED = "Subscribed";
    private final int maxAge;

    public AuthCookiesService(
          @Value("${jwt.expiration}") int maxAge) {

        this.maxAge = maxAge;

    }

    /**
     * Add authentication success cookies.
     * @param response HttpServletResponse
     * @param token    JWT token
     */
    void addAuthSucceededCookies(
          HttpServletResponse response,
          String token) {

        addCookie(
              response,
              AUTHORIZATION,
              token,
              true,
              maxAge
        );

        addCookie(
              response,
              AUTHENTICATED,
              "true",
              false,
              maxAge
        );

    }

    /**
     * Add authentication fail cookies.
     * @param response HttpServletResponse
     */
    void addAuthFailedCookies(
          HttpServletResponse response) {

        addCookie(
              response,
              AUTHORIZATION,
              null,
              true,
              0
        );

        addCookie(
              response,
              AUTHENTICATED,
              "false",
              false,
              Integer.MAX_VALUE
        );

    }

    /**
     * Reset authentication cookies,
     * @param response HttpServletResponse
     */
    void resetAuthCookies(
          HttpServletResponse response) {

        addCookie(
              response,
              AUTHORIZATION,
              null,
              true,
              0
        );

        addCookie(
              response,
              AUTHENTICATED,
              "false",
              false,
              Integer.MAX_VALUE
        );

        addCookie(
              response,
              SUBSCRIBED,
              null,
              true,
              0
        );

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
              value
        );

        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);

    }

}

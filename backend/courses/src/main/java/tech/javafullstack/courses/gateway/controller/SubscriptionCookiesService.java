// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.gateway.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Subscription cookie service.
 */
@Service
class SubscriptionCookiesService {

    private static final String SUBSCRIPTION = "Subscription";
    private final int maxAge;

    public SubscriptionCookiesService(
          @Value("${jwt.expiration}") int maxAge) {

        this.maxAge = maxAge;

    }

    /**
     * Add Subscribed cookie to http response.
     * @param response   Http response
     * @param subscribed Subscribed cookie value
     */
    void addSubscriptionCookie(
          HttpServletResponse response,
          String value) {

        addCookie(
              response,
              SUBSCRIPTION,
              value,
              false,
              maxAge
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

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class SubscriptionCookiesService {

    private static final String SUBSCRIBED = "Subscribed";
    private final int maxAge;

    public SubscriptionCookiesService(
          @Value("${jwt.expiration}") int maxAge) {

        this.maxAge = maxAge;

    }

    void addASubscriptionCookie(
          HttpServletResponse response,
          boolean subscribed) {

        addCookie(
              response,
              SUBSCRIBED,
              String.valueOf(subscribed),
              false,
              maxAge);

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

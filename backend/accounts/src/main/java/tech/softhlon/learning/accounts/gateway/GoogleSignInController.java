// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.GoogleSignInService;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import java.util.Map;

import static tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.Succeeded;
import static tech.softhlon.learning.accounts.gateway.RestResources.GOOGLE_SIGN_IN;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class GoogleSignInController {
    private final GoogleSignInService service;
    private final HttpServletRequest httpRequest;
    @Value("${login-redirect-uri}")
    private String loginRedirectUri;

    @PostMapping(path = GOOGLE_SIGN_IN, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    void signIn(@RequestParam Map<String, String> body, HttpServletResponse response) {
        log.info("Requested, body{}", body);
        var result = service.execute(new GoogleSignInService.Request(body.get("credential"), null));
        if (result instanceof Succeeded(String token)) {
            addAuthSucceededCookies(response, token);
        } else {
            addAuthFailedCookies(response);
        }
        addRedirectHeaders(response);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void addAuthSucceededCookies(HttpServletResponse response, String token) {
        addCookie(response, "Authorization", token, true);
        addCookie(response, "Authenticated", "true", false);
    }

    private void addAuthFailedCookies(HttpServletResponse response) {
        addCookie(response, "Authorization", null, true);
        addCookie(response, "Authenticated", "false", false);
    }

    private void addRedirectHeaders(HttpServletResponse response) {
        response.setHeader("Location", loginRedirectUri);
        response.setStatus(HttpStatus.FOUND.value());
    }

    private void addCookie(
          HttpServletResponse response,
          String name,
          String value,
          boolean httpOnly) {
        var cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }
}

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.SignInService;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import static tech.softhlon.learning.accounts.domain.SignInService.Result.*;
import static tech.softhlon.learning.accounts.gateway.RestResources.SIGN_IN;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SignInController {
    private final SignInService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(SIGN_IN)
    ResponseEntity<?> signIn(@Validated @RequestBody SignInService.Request request,HttpServletResponse response) {
        log.info("Requested, body: {}", request);
        var result = service.execute(request);
        return switch (result) {
            case Succeeded(String token) -> success(response, token);
            case InvalidCredentialsFailed() -> fail(response);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> success(HttpServletResponse response, String token) {
        addAuthSucceededCookies(response, token);
        return successOkBody();
    }

    private ResponseEntity<?> fail(HttpServletResponse response) {
        addAuthFailedCookies(response);
        return unauthorizedBody();
    }

    private void addAuthSucceededCookies(HttpServletResponse response, String token) {
        addCookie(response, "Authorization", token, true);
        addCookie(response, "Authenticated", "true", false);
    }

    private void addAuthFailedCookies(HttpServletResponse response) {
        addCookie(response, "Authorization", null, true);
        addCookie(response, "Authenticated", "false", false);
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

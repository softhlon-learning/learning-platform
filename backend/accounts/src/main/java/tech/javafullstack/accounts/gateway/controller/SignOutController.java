// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.gateway.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.accounts.domain.SignOutService;
import tech.javafullstack.accounts.domain.SignOutService.Result.Failed;
import tech.javafullstack.accounts.domain.SignOutService.Result.NotAuthorized;
import tech.javafullstack.accounts.domain.SignOutService.Result.Succeeded;
import tech.javafullstack.common.hexagonal.RestApiAdapter;

import static tech.javafullstack.accounts.gateway.controller.AuthCookiesService.AUTHORIZATION;
import static tech.javafullstack.accounts.gateway.controller.RestResources.SIGN_OUT;
import static tech.javafullstack.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Sign out controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SignOutController {

    private final SignOutService service;
    private final AuthCookiesService authCookiesService;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/auth/sign-out endpoint.
     * @param response HttpServletResponse
     * @return ResponseEntity<?>
     */
    @PostMapping(SIGN_OUT)
    ResponseEntity<?> signOut(
          HttpServletResponse response) {

        log.info("controller | request / Sign out");

        var result = service.execute(extractToken());
        authCookiesService.resetAuthCookies(response);

        return switch (result) {
            case Succeeded() -> successCreatedBody();
            case NotAuthorized(String message) -> unAuthorizedBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private String extractToken() {

        var cookies = httpRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTHORIZATION)) {
                return cookie.getValue();
            }
        }

        return null;

    }

}

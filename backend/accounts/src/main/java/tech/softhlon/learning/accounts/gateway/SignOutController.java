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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.SignOutService;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import static tech.softhlon.learning.accounts.domain.SignOutService.Result.Failed;
import static tech.softhlon.learning.accounts.domain.SignOutService.Result.Succeeded;
import static tech.softhlon.learning.accounts.gateway.RestResources.SIGN_OUT;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.successCreatedBody;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SignOutController {
    private final SignOutService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(SIGN_OUT)
    ResponseEntity<?> signOut(@Validated @RequestBody SignOutService.Request request, HttpServletResponse response) {
        log.info("Requested, body: {}", request);
        var result = service.execute(request);
        return switch (result) {
            case Succeeded() -> successResponse(response);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> successResponse(HttpServletResponse response) {
        resetAuthCookies(response);
        return successCreatedBody();
    }

    private void resetAuthCookies(HttpServletResponse response) {
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

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.SignInService;
import tech.softhlon.learning.accounts.domain.SignInService.Result.Failed;
import tech.softhlon.learning.accounts.domain.SignInService.Result.InvalidCredentialsFailed;
import tech.softhlon.learning.accounts.domain.SignInService.Result.Succeeded;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

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
    private final AuthCookiesService authCookiesService;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/auth/sign-in endpoint.
     */
    @PostMapping(SIGN_IN)
    ResponseEntity<?> signIn(
          @Validated @RequestBody SignInService.Request request,
          HttpServletResponse response) {

        log.info("Requested, body: {}",
              request);

        var result = service.execute(
              request);

        return switch (result) {
            case Succeeded(String token) -> success(response, token);
            case InvalidCredentialsFailed(String message) -> fail(response, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> success(
          HttpServletResponse response,
          String token) {

        authCookiesService.addAuthSucceededCookies(
              response,
              token);

        return successOkBody();

    }

    private ResponseEntity<?> fail(
          HttpServletResponse response,
          String message) {

        authCookiesService.addAuthFailedCookies(
              response);

        return unAuthorizedBody(
              httpRequest,
              message);

    }

}

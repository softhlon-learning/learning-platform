// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.accounts.domain.SignInService;
import tech.javafullstack.accounts.domain.SignInService.Result.EmailPolicyFailed;
import tech.javafullstack.accounts.domain.SignInService.Result.Failed;
import tech.javafullstack.accounts.domain.SignInService.Result.InvalidCredentialsFailed;
import tech.javafullstack.accounts.domain.SignInService.Result.Succeeded;
import tech.javafullstack.common.hexagonal.RestApiAdapter;

import static tech.javafullstack.accounts.gateway.controller.RestResources.SIGN_IN;
import static tech.javafullstack.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Sign in controller.
 */
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
     * @param request  Request
     * @param response
     * @return HttpServletResponse
     */
    @PostMapping(SIGN_IN)
    ResponseEntity<?> signIn(
          @Validated @RequestBody Request request,
          HttpServletResponse response) {

        log.info("controller | request / Sign in, {}",
              request);

        var result = service.execute(
              request.email(),
              request.password()
        );

        return switch (result) {
            case Succeeded(String token) -> success(response, token);
            case EmailPolicyFailed(String message) -> fail(response, message);
            case InvalidCredentialsFailed(String message) -> fail(response, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    record Request(
          String email,
          String password) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> success(
          HttpServletResponse response,
          String token) {

        authCookiesService.addAuthSucceededCookies(
              response,
              token
        );

        return successOkBody();

    }

    private ResponseEntity<?> fail(
          HttpServletResponse response,
          String message) {

        authCookiesService.addAuthFailedCookies(response);
        return unAuthorizedBody(
              httpRequest,
              message
        );

    }

}

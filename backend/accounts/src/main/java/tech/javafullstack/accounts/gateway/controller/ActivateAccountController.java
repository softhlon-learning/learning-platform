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
import tech.javafullstack.accounts.domain.ActivateAccountService;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.ExpiredTokenFailed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.Failed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.InvalidTokenFailed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.Succeeded;
import tech.javafullstack.common.hexagonal.RestApiAdapter;

import static tech.javafullstack.accounts.gateway.controller.RestResources.ACTIVATE_ACCOUNT;
import static tech.javafullstack.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Activate account controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class ActivateAccountController {

    private final ActivateAccountService service;
    private final AuthCookiesService authCookiesService;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/activate endpoint.
     * @param request  PasswordUpdate
     * @param response ActivateAccount
     * @return ResponseEntity<?>
     */
    @PostMapping(ACTIVATE_ACCOUNT)
    ResponseEntity<?> acticateAccount(
          @Validated @RequestBody ActivateAccount request,
          HttpServletResponse response) {

        log.info("controller | request / Activate account, {}",
              request);

        var result = service.execute(request.token());
        return switch (result) {
            case Succeeded(String authToken) -> success(response, authToken);
            case ExpiredTokenFailed(String message) -> badRequestBody(httpRequest, message);
            case InvalidTokenFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    record ActivateAccount(
          String token) {

        @Override
        public String toString() {
            return """
                  [token: %s]"""
                  .formatted(token);
        }

    }

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

}

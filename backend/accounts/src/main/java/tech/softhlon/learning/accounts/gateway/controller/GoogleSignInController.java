// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.GoogleSignInService;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.AccountIsDeletedFailed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.Failed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.InvalidCredentialsFailed;
import tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.Succeeded;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import java.util.Map;

import static tech.softhlon.learning.accounts.gateway.controller.RestResources.GOOGLE_SIGN_IN;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Google sing in controller.
 */
@Slf4j
@RestApiAdapter
@RestController
class GoogleSignInController {

    private static final String LOCATION = "Location";
    private static final String CREDENTIAL = "credential";
    private final GoogleSignInService service;
    private final AuthCookiesService authCookiesService;
    private final HttpServletRequest httpRequest;
    private final String loginRedirectSuccessUri;
    private final String loginRedirectFailUri;

    public GoogleSignInController(
          GoogleSignInService service,
          AuthCookiesService authCookiesService,
          HttpServletRequest httpRequest,
          @Value("${login-redirect-success-uri}") String loginRedirectSuccessUri,
          @Value("${login-redirect-fail-uri}") String loginRedirectFailUri) {

        this.service = service;
        this.authCookiesService = authCookiesService;
        this.httpRequest = httpRequest;
        this.loginRedirectSuccessUri = loginRedirectSuccessUri;
        this.loginRedirectFailUri = loginRedirectFailUri;

    }

    /**
     * POST /api/v1/account/auth/google-sign-in endpoint.
     */
    @PostMapping(path = GOOGLE_SIGN_IN, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    void signIn(
          @RequestParam Map<String, String> body,
          HttpServletResponse response) {

        log.info("controller | request / Google sign-in");

        var result = service.execute(body.get(CREDENTIAL));

        switch (result) {
            case Succeeded(String token) -> {

                authCookiesService.addAuthSucceededCookies(response, token);
                addSuccessfulRedirectHeaders(response);

            }
            case Failed(_), InvalidCredentialsFailed(_) -> {

                authCookiesService.addAuthFailedCookies(response);
                addFailfulRedirectHeaders(response, "Internal error");

            }
            case AccountIsDeletedFailed(String message) -> {

                authCookiesService.addAuthFailedCookies(response);
                addFailfulRedirectHeaders(response, message);

            }

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void addSuccessfulRedirectHeaders(
          HttpServletResponse response) {

        response.setHeader(
              LOCATION,
              loginRedirectSuccessUri
        );

        response.setStatus(
              HttpStatus.FOUND.value());

    }

    private void addFailfulRedirectHeaders(
          HttpServletResponse response,
          String errorMessage) {

        response.setHeader(
              LOCATION,
              loginRedirectFailUri + "?error=" + errorMessage
        );

        response.setStatus(
              HttpStatus.FOUND.value());

    }

}

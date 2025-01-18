// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

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
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import java.util.Map;

import static tech.softhlon.learning.accounts.domain.GoogleSignInService.Request;
import static tech.softhlon.learning.accounts.domain.GoogleSignInService.Result.*;
import static tech.softhlon.learning.accounts.gateway.RestResources.GOOGLE_SIGN_IN;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
class GoogleSignInController {
    private static final String LOCATION = "Location";
    private static final String CREDENTIAL = "credential";
    private final GoogleSignInService service;
    private final AuthCookiesService authCookiesService;
    private final HttpServletRequest httpRequest;
    private final String loginRedirectUri;

    public GoogleSignInController(
          GoogleSignInService service,
          AuthCookiesService authCookiesService,
          HttpServletRequest httpRequest,
          @Value("${login-redirect-uri}") String loginRedirectUri) {
        this.service = service;
        this.authCookiesService = authCookiesService;
        this.httpRequest = httpRequest;
        this.loginRedirectUri = loginRedirectUri;
    }

    /**
     * POST /api/v1/account/auth/google-sign-in endpoint.
     */
    @PostMapping(path = GOOGLE_SIGN_IN, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    void signIn(@RequestParam Map<String, String> body, HttpServletResponse response) {
        log.info("Requested");
        var result = service.execute(prepareRequest(body));
        switch (result) {
            case Succeeded(String token) -> authCookiesService.addAuthSucceededCookies(response, token);
            case Failed(_), InvalidCredentialsFailed(_) -> authCookiesService.addAuthFailedCookies(response);
        }
        addRedirectHeaders(response);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Request prepareRequest(Map<String, String> body) {
        return new Request(body.get(CREDENTIAL));
    }

    private void addRedirectHeaders(HttpServletResponse response) {
        response.setHeader(LOCATION, loginRedirectUri);
        response.setStatus(HttpStatus.FOUND.value());
    }
}

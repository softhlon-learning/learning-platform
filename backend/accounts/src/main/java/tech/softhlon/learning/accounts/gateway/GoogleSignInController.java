// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

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
            response.setHeader("Set-Cookie", "Authorization=" + token + "; Path=/; Secure; HttpOnly");
        }

        response.setHeader("Location", loginRedirectUri);
        response.setStatus(HttpStatus.FOUND.value());
    }
}

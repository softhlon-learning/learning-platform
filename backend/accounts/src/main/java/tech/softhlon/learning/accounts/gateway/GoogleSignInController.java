// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import tech.softhlon.learning.accounts.domain.GoogleSignInService;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Version;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static tech.softhlon.learning.accounts.gateway.RestResources.GOOGLE_SIGN_IN;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

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
        service.signIn(new GoogleSignInService.Request(body.get("credential"), null));
        response.setHeader("Location", loginRedirectUri);
        response.setStatus(302);
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    ResponseEntity<?> signIn(@Validated @RequestBody SignInService.Request request) {
        log.info("Requested, body: {}", request);
        var result = service.execute(request);
        return switch (result) {
            case Succeeded() -> successOkBody();
            case InvalidCredentialsFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

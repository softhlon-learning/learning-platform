// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.gateway;

import io.softhlon.learning.accounts.domain.SignInService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.accounts.domain.SignInService.Result.*;
import static io.softhlon.learning.accounts.gateway.RestResources.SIGN_IN;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class SignInController {
    private final SignInService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(SIGN_IN)
    ResponseEntity<?> signIn(@Validated @RequestBody SignInService.Request request) {
        var result = service.signIn(request);
        return switch (result) {
            case Success() -> successOkBody();
            case InvalidCredentials(String message) -> badRequestBody(httpRequest, message);
            case InternalFailure(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

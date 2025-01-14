// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import tech.softhlon.learning.accounts.domain.SignUpService;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static tech.softhlon.learning.accounts.domain.SignUpService.Result.*;
import static tech.softhlon.learning.accounts.gateway.RestResources.SIGN_UP;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SignUpController {
    private final SignUpService service;
    private final HttpServletRequest httpRequest;

    private static ResponseEntity<Response> successBody(UUID id) {
        return status(HttpStatus.CREATED).body(new Response(id));
    }

    @PostMapping(SIGN_UP)
    ResponseEntity<?> signUp(@Validated @RequestBody SignUpService.Request request) {
        var result = service.signUp(request);
        return switch (result) {
            case Succeeded(UUID id) -> successBody(id);
            case AccountAlreadyExistsFailed(String message) -> badRequestBody(httpRequest, message);
            case PasswordPolicyFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------
    record Response(UUID accountId) {}
}

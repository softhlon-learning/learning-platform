// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.gateway;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import io.softhlon.learning.accounts.domain.SignInService;
import io.softhlon.learning.accounts.domain.SignUpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.accounts.domain.SignUpService.Result.*;
import static io.softhlon.learning.accounts.gateway.RestResources.SIGN_UP;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
class SignUpController {
    private final SignUpService signUpService;
    private final HttpServletRequest servletRequest;

    @PostMapping(SIGN_UP)
    ResponseEntity<?> signUp(@Validated @RequestBody SignUpService.Request request) {
        var result = signUpService.signUp(request);

        return switch (result) {
            case Success() -> successBody();
            case AccountAlreadyExists(String message) -> badRequestBody(servletRequest, message);
            case PasswordPolicyFailure(String message) -> badRequestBody(servletRequest, message);
            case InternalFailure(Throwable cause) -> internalServerBody(servletRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private static ResponseEntity<SignInService.Result> successBody() {
        return status(HttpStatus.CREATED).build();
    }
}

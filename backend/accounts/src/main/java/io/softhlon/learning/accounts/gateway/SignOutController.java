// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.gateway;

import io.softhlon.learning.accounts.domain.SignInService;
import io.softhlon.learning.accounts.domain.SignOutService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.accounts.domain.SignOutService.Result.InternalFailure;
import static io.softhlon.learning.accounts.domain.SignOutService.Result.Success;
import static io.softhlon.learning.accounts.gateway.RestResources.SIGN_OUT;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class SignOutController {
    private final SignOutService signOutService;
    private final HttpServletRequest servletRequest;

    @PostMapping(SIGN_OUT)
    ResponseEntity<?> signOut(@Validated @RequestBody SignOutService.Request request) {
        var result = signOutService.signOut(request);

        return switch (result) {
            case Success() -> successBody();
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

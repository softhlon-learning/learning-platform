// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.gateway;

import io.softhlon.learning.accounts.domain.SignOutService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.accounts.domain.SignOutService.Result.*;
import static io.softhlon.learning.accounts.gateway.RestResources.*;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.*;

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
            case Success() -> successOkBody();
            case InternalFailure(Throwable cause) -> internalServerBody(servletRequest, cause);
        };
    }
}

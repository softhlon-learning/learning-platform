// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.gateway;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import io.softhlon.learning.accounts.domain.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static io.softhlon.learning.accounts.gateway.RestResources.ACCOUNT;
import static io.softhlon.learning.accounts.gateway.RestResources.SIGN_IN;

@RequestMapping(ACCOUNT)
@RequiredArgsConstructor
class SignInController {
    private final SignInService signInService;

    @PostMapping(SIGN_IN)
    ResponseEntity<?> signIn(@Validated @RequestBody SignInService.Request request) {
        throw new UnsupportedOperationException();
    }
}

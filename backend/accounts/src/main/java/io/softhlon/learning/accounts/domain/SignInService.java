// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import io.softhlon.learning.common.hexagonal.InboundPort;

@InboundPort
@FunctionalInterface
public interface SignInService {
    Result signIn(Request request);

    record Request(String email, String password) {}
    sealed interface Result {
        record Success() implements Result {}
        record InvalidCredentials(String message) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}

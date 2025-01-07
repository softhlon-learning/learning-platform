// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import io.softhlon.learning.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface SignInService {
    Result signIn(Request request);

    record Request(
          String email,
          String password) {}

    sealed interface Result {
        record Succeeded() implements Result {}
        record InvalidCredentialsFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
}

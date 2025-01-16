// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface SignInService {
    Result execute(Request request);

    sealed interface Result {
        record Succeeded() implements Result {}
        record InvalidCredentialsFailed() implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
    record Request(
          String email,
          String password) {}
}

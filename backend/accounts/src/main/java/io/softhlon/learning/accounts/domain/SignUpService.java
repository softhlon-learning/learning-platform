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
public interface SignUpService {
    Result signUp(Request request);

    record Request(String name, String email, String password) {}
    sealed interface Result {
        record Success() implements Result {}
        record AccountAlreadyExistsFailure(String message) implements Result {}
        record PasswordPolicyFailure(String message) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}

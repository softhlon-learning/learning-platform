// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import io.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface SignUpService {
    Result signUp(Request request);

    record Request(String name, String email, String password) {}
    sealed interface Result {
        record Success(UUID id) implements Result {}
        record AccountAlreadyExists(String message) implements Result {}
        record PasswordPolicyFailure(String message) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}

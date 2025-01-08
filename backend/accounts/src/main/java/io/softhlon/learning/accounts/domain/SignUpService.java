// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
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
        record Succeeded(UUID id) implements Result {}
        record AccountAlreadyExistsFailed(String message) implements Result {}
        record PasswordPolicyFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
}

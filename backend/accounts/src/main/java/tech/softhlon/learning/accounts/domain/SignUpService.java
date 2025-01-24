// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface SignUpService {

    Result execute(
          Request request);

    sealed interface Result {
        record Succeeded(UUID id, String token) implements Result {}
        record AccountAlreadyExistsFailed(String message) implements Result {}
        record AccountIsDeletedFailed(String message) implements Result {}
        record NamePolicyFailed(String message) implements Result {}
        record EmailPolicyFailed(String message) implements Result {}
        record PasswordPolicyFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Request(
          String name,
          String email,
          String password) {}

}

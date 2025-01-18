// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

@InboundPort
@FunctionalInterface
public interface DeleteAccountService {
    Result execute(Request request);

    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotFoundFailed(String message) implements Result {}
        record AccountIsAlreadyDeletedFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
    record Request(UUID accountId) {}
}

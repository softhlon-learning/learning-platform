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
public interface DeleteAccountService {

    Result execute(
          UUID accountId);

    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotFoundFailed(String message) implements Result {}
        record AccountIsAlreadyDeletedFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

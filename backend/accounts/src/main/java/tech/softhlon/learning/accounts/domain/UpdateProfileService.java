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
public interface UpdateProfileService {

    Result execute(
          UUID accountId,
          String name);

    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

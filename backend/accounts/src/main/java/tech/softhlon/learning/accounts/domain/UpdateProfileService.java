// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Updated profile service interface.
 */
@InboundPort
@FunctionalInterface
public interface UpdateProfileService {

    /**
     * Update user's profile.
     * @param accountId Account Id
     * @param name      User's name
     * @return Result
     */
    Result execute(
          UUID accountId,
          String name);

    /**
     * Update user's profile result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

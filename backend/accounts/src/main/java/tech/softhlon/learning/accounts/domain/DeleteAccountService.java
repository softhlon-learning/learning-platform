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
 * Delete account service interface.
 */
@InboundPort
@FunctionalInterface
public interface DeleteAccountService {

    /**
     * Delete account (mark as deleted).
     * @param accountId Account Id
     * @return Result
     */
    Result execute(
          UUID accountId);

    /**
     * Delete account result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotFoundFailed(String message) implements Result {}
        record AccountIsAlreadyDeletedFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

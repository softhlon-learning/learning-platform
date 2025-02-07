// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Update account active flag repository interface.
 */
@InboundPort
@FunctionalInterface
interface UpdateAccountActiveFlagRepository {

    /**
     * Update account active flag in repository.
     * @param accountId
     * @param isActive
     * @return UpdateActiveFlagResult
     */
    UpdateActiveFlagResult execute(
          UUID accountId,
          boolean isActive);

    /**
     * Update account active flag in repository result.
     */
    sealed interface UpdateActiveFlagResult {
        record ActiveFlagUpdated() implements UpdateActiveFlagResult {}
        record ActiveFlagUpdateFailed(Throwable cause) implements UpdateActiveFlagResult {}
    }

}

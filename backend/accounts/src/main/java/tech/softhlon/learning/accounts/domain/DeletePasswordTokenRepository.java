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
 * Delete password token repository interface.
 */
@InboundPort
@FunctionalInterface
public interface DeletePasswordTokenRepository {

    /**
     * Delete password token in repository.
     * @param id Password token id
     * @return DeletePasswordTokenResult
     */
    DeletePasswordTokenResult execute(
          UUID id);

    /**
     * Delete apssword token result.
     */
    sealed interface DeletePasswordTokenResult {
        record TokenDeleted() implements DeletePasswordTokenResult {}
        record TokenDeletionFailed(Throwable cause) implements DeletePasswordTokenResult {}
    }

}

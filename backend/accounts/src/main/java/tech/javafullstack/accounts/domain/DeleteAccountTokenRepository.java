// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Delete account activation token repository interface.
 */
@InboundPort
@FunctionalInterface
public interface DeleteAccountTokenRepository {

    /**
     * Delete account activation token in repository.
     * @param id Account activation token id
     * @return DeletePasswordTokenResult
     */
    DeleteAccountTokenResult execute(
          UUID id);

    /**
     * Delete account activation token result.
     */
    sealed interface DeleteAccountTokenResult {
        record TokenDeleted() implements DeleteAccountTokenResult {}
        record TokenDeletionFailed(Throwable cause) implements DeleteAccountTokenResult {}
    }

}

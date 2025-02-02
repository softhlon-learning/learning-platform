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

@InboundPort
@FunctionalInterface
public interface DeletePasswordTokenRepository {

    DeletePasswordTokenResult execute(
          UUID id);

    sealed interface DeletePasswordTokenResult {
        record TokenDeleted() implements DeletePasswordTokenResult {}
        record TokenDeletionFailed(Throwable cause) implements DeletePasswordTokenResult {}
    }

}

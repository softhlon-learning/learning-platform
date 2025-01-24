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
interface DeletePasswordTokenRepository {

    DeletePasswordTokenResult execute(
          DeletePasswordTokenRequest request);

    record DeletePasswordTokenRequest(UUID id) {}

    sealed interface DeletePasswordTokenResult {
        record Succeeded() implements DeletePasswordTokenResult {}
        record Failed(Throwable cause) implements DeletePasswordTokenResult {}
    }

}

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
public interface DeleteAccountRepository {
    DeleteAccountRequest execute(DeleteAccountRequest request);

    sealed interface DeleteAccountResult {
        record AccountPersisted(UUID uuid) implements DeleteAccountResult {}
        record AccountPersistenceFailed(Throwable cause) implements DeleteAccountResult {}
    }
    record DeleteAccountRequest(UUID id) {}
}

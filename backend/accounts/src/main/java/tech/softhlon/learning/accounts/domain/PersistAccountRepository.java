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
public interface PersistAccountRepository {
    PersistAccountResult execute(PersistAccountRequest request);

    sealed interface PersistAccountResult {
        record AccountPersisted(UUID uuid) implements PersistAccountResult {}
        record AccountNotFoundInDatabase() implements PersistAccountResult {}
        record AccountPersistenceFailed(Throwable cause) implements PersistAccountResult {}
    }

    record PersistAccountRequest(
          UUID id,
          String type,
          String name,
          String email,
          String password,
          boolean isDeleted) {}
}

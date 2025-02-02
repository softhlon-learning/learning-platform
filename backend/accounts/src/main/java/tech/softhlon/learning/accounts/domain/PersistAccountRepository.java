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
public interface PersistAccountRepository {

    /**
     * Persist account in repository.
     * @param request Account data
     * @return PersistAccountResult
     */
    PersistAccountResult execute(
          PersistAccountRequest request);

    /**
     * Persist account in repository result.
     */
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

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.InboundPort;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist account repository interface.
 */
@OutboundPort
@DomainRepository
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

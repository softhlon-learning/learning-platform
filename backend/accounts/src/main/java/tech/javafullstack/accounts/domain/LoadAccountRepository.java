// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load account repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadAccountRepository {

    /**
     * Load account by id from repository.
     * @param accountId Account Id
     * @return LoadAccountResult
     */
    LoadAccountResult execute(
          UUID accountId);

    /**
     * Load account by id from repository result.
     */
    sealed interface LoadAccountResult {
        record AccountLoaded(Account account) implements LoadAccountResult {}
        record AccountNotFound() implements LoadAccountResult {}
        record AccountLoadFailed(Throwable cause) implements LoadAccountResult {}
    }

    record Account(
          UUID id,
          String type,
          String name,
          String email,
          String password,
          boolean isDeleted) {}

}

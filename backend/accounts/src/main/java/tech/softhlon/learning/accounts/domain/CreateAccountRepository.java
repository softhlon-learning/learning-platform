// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
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
 * Create account repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CreateAccountRepository {

    /**
     * Create account in repository.
     * @param type     Account type (GOOGLE, PASSWORD, etc...)
     * @param name     User's name
     * @param email    User's email
     * @param password User's password
     * @return CreateAccountResult
     */
    CreateAccountResult execute(
          String type,
          String name,
          String email,
          String password);

    /**
     * Create account result.
     */
    sealed interface CreateAccountResult {
        record AccountPersisted(UUID uuid) implements CreateAccountResult {}
        record AccountPersistenceFailed(Throwable cause) implements CreateAccountResult {}
    }

}

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
 * Checkout account by email repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CheckAccountByEmailRepository {

    /**
     * Check if account associated with given email exists in repostitory.
     * @param email Email to be checked
     * @return CheckAccountByEmailResult
     */
    CheckAccountByEmailResult execute(
          String email);

    /**
     * Check account by email result.
     */
    sealed interface CheckAccountByEmailResult {
        record AccountExists(UUID accountId) implements CheckAccountByEmailResult {}
        record AccountIsDeleted() implements CheckAccountByEmailResult {}
        record AccountNotFound() implements CheckAccountByEmailResult {}
        record CheckAccountFailed(Throwable cause) implements CheckAccountByEmailResult {}
    }

}

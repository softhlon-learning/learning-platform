// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load account by email repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadAccountByEmailRepository {

    /**
     * Load account by user's email from repository.
     * @param email User's email
     * @return LoadAccountByEmailResult
     */
    LoadAccountByEmailResult execute(
          String email);

    /**
     * Load account by user's email from repository result.
     */
    sealed interface LoadAccountByEmailResult {
        record AccountFound(Account account) implements LoadAccountByEmailResult {}
        record AccountNotFound() implements LoadAccountByEmailResult {}
        record AccountIsDeleted() implements LoadAccountByEmailResult {}
        record LoadAccountFailed(Throwable cause) implements LoadAccountByEmailResult {}
    }

    record LoadAccountByEmailRequest(
          String email) {}

    record Account(
          UUID id,
          String email,
          String password) {}

}

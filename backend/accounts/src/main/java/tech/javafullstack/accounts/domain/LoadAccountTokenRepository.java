// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load account token repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadAccountTokenRepository {

    /**
     * Load account activation token from repository.
     * @param token Account activation token
     * @return LoadAccountTokenResult
     */
    LoadAccountTokenResult execute(
          String token);

    /**
     * Load account activation token from repository result.
     */
    sealed interface LoadAccountTokenResult {
        record TokenLoaded(AccountToken passwordToken) implements LoadAccountTokenResult {}
        record TokenNotFound() implements LoadAccountTokenResult {}
        record TokenLoadFailed(Throwable cause) implements LoadAccountTokenResult {}
    }

    record AccountToken(
          UUID id,
          UUID accountId,
          String token,
          OffsetDateTime expirationTime) {}

}

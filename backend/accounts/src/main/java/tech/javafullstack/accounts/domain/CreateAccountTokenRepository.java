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
 * Create account activation token repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CreateAccountTokenRepository {

    /**
     * Create account activation token in repository.
     * @param accountId      Account id
     * @param token          Token to store in respository
     * @param expirationTime Token expiration time
     * @return CreatePasswordTokenResult
     */
    CreateAccountTokenResult execute(
          UUID accountId,
          String token,
          OffsetDateTime expirationTime);

    /**
     * Create account activation token result.
     */
    sealed interface CreateAccountTokenResult {
        record AccountTokenPersisted() implements CreateAccountTokenResult {}
        record AccountTokenPersistenceFailed(Throwable cause) implements CreateAccountTokenResult {}
    }

}

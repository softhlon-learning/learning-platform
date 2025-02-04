// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create password token repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CreatePasswordTokenRepository {

    /**
     * Create password token in repository.
     * @param accountId      Account id
     * @param token          Token to store in respository
     * @param expirationTime Token expiration time
     * @return CreatePasswordTokenResult
     */
    CreatePasswordTokenResult execute(
          UUID accountId,
          String token,
          OffsetDateTime expirationTime);

    /**
     * Create password token result.
     */
    sealed interface CreatePasswordTokenResult {
        record PasswordTokenPersisted() implements CreatePasswordTokenResult {}
        record PasswordTokenPersistenceFailed(Throwable cause) implements CreatePasswordTokenResult {}
    }

}

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
 * Load password token repositry interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadPasswordTokenRepository {

    /**
     * Load password token from repository.
     * @param token Password token
     * @return LoadPasswordTokenResult
     */
    LoadPasswordTokenResult execute(
          String token);

    /**
     * Load password token from repository result.
     */
    sealed interface LoadPasswordTokenResult {
        record TokenLoaded(PasswordToken passwordToken) implements LoadPasswordTokenResult {}
        record TokenNotFound() implements LoadPasswordTokenResult {}
        record TokenLoadFailed(Throwable cause) implements LoadPasswordTokenResult {}
    }

    record PasswordToken(
          UUID id,
          UUID accountId,
          String token,
          OffsetDateTime expirationTime) {}

}

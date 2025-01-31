// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadPasswordTokenRepository {

    LoadPasswordTokenResult execute(
          String token);

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

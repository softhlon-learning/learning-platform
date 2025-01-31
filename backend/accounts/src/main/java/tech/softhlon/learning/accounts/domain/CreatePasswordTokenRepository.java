// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CreatePasswordTokenRepository {

    CreatePasswordTokenResult execute(
          UUID accountId,
          String token,
          OffsetDateTime expirationTime);

    sealed interface CreatePasswordTokenResult {
        record PasswordTokenPersisted() implements CreatePasswordTokenResult {}
        record PasswordTokenPersistenceFailed(Throwable cause) implements CreatePasswordTokenResult {}
    }

}

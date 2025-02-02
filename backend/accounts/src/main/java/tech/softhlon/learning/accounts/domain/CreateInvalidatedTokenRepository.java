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

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CreateInvalidatedTokenRepository {

    /**
     * Create invalidated token in repository.
     * @param tokenHash Hash of invalidated token
     * @return CreateInvalidatedTokenResult
     */
    CreateInvalidatedTokenResult execute(
          String tokenHash);

    /**
     * Create invalidated token result.
     */
    sealed interface CreateInvalidatedTokenResult {
        record InvalidatedTokenPersisted(UUID uuid) implements CreateInvalidatedTokenResult {}
        record InvalidatedTokenPersistenceFailed(Throwable cause) implements CreateInvalidatedTokenResult {}
    }

}

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
 * Create invalidated token repository interface.
 */
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

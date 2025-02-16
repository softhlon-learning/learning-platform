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
 * Persist contact message repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface PersistContactMessageRepository {

    /**
     * Persist contact message in repository.
     * @param request Contact message
     * @return PersistContactMessageResult
     */
    PersistContactMessageResult execute(
          PersistContactMessageRequest request);

    /**
     * Persist contact message in repository result.
     */
    sealed interface PersistContactMessageResult {
        record ContactMessagePersisted(UUID uuid) implements PersistContactMessageResult {}
        record ContactMessagePersistenceFailed(Throwable cause) implements PersistContactMessageResult {}
    }

    record PersistContactMessageRequest(
          String subject,
          String email,
          String message) {}

}

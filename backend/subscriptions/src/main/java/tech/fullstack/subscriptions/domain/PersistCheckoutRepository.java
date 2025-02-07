// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist checkout session repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface PersistCheckoutRepository {

    /**
     * Persist checkout session in repository.
     * @param request Operation request
     * @return PersistCheckoutSessionResult
     */
    PersistCheckoutSessionResult execute(
          PersistCheckoutSessionRequest request);

    /**
     * Persist checkout session in repository result.
     */
    sealed interface PersistCheckoutSessionResult {
        record CheckoutSessionPersisted() implements PersistCheckoutSessionResult {}
        record CheckoutSessionPersistenceFailed(Throwable cause) implements PersistCheckoutSessionResult {}
    }

    record PersistCheckoutSessionRequest(
          UUID id,
          String sessionId,
          UUID accountId,
          OffsetDateTime expiredTime,
          OffsetDateTime completedTime) {}

}

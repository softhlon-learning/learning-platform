// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist subscription repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface PersistSubscriptionRepository {

    /**
     * Persist subscription in repository.
     * @param request Operation request
     * @return PersistSubscriptionResult
     */
    PersistSubscriptionResult execute(
          PersistSubscriptionRequest request);

    /**
     * Persist subscription in repository result.
     */
    sealed interface PersistSubscriptionResult {
        record SubscriptionPersisted() implements PersistSubscriptionResult {}
        record SubscriptionPersistenceFailed(Throwable cause) implements PersistSubscriptionResult {}
    }

    record PersistSubscriptionRequest(
          UUID id,
          String subscriptionId,
          String customerId,
          boolean active,
          OffsetDateTime canceledAt,
          OffsetDateTime cancelAt,
          String cancelReason,
          OffsetDateTime periodStartAt,
          OffsetDateTime periodEndAt,
          String invoiceId) {}

}

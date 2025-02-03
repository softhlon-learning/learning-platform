// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist subscription repository interface.
 */
@OutboundPort
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

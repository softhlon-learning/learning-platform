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
 * Load subscription repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadSubscriptionRepository {

    /**
     * Load subsciption from repository.
     * @param subscriptionId Subscription Id
     * @return LoadSubscriptionResult
     */
    LoadSubscriptionResult execute(
          String subscriptionId);

    /**
     * Load subsciption from repository result.
     */
    sealed interface LoadSubscriptionResult {
        record SubscriptionLoaded(Subscription subscription) implements LoadSubscriptionResult {}
        record SubscriptionNotFound() implements LoadSubscriptionResult {}
        record SubscriptionLoadFailed(Throwable cause) implements LoadSubscriptionResult {}
    }

    record Subscription(
          UUID id,
          String subscriptionId,
          String customerId,
          boolean active,
          OffsetDateTime canceledAt,
          OffsetDateTime cancelAt,
          String cancelReason,
          OffsetDateTime periodStartAt,
          OffsetDateTime periodEndAt,
          String invloiceId) {}

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

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
public interface LoadSubscriptionRepository {
    LoadSubscriptionResult execute(
          LoadSubscriptionRequest request);

    sealed interface LoadSubscriptionResult {
        record SubscriptionLoaded(Subscription subscription) implements LoadSubscriptionResult {}
        record SubscriptionNotFound() implements LoadSubscriptionResult {}
        record SubscriptionLoadFailed(Throwable cause) implements LoadSubscriptionResult {}
    }

    record LoadSubscriptionRequest(
          UUID accountId) {}

    record Subscription(
          UUID id,
          String subscriptionId,
          UUID accountId,
          boolean active,
          OffsetDateTime activatedTime,
          OffsetDateTime deactivateTime) {}

}

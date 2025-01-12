// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface UpdateSubscriptionRepository {
    UpdateSubscriptionResult execute(Subscription subscription);

    sealed interface UpdateSubscriptionResult {
        record SubscriptionPersisted() implements UpdateSubscriptionResult {}
        record SubscriptionPersistenceFailed(Throwable cause) implements UpdateSubscriptionResult {}
    }
    record Subscription(
          UUID id,
          UUID accountId,
          String status,
          OffsetDateTime startedTime,
          OffsetDateTime cancelledTime) {}
}

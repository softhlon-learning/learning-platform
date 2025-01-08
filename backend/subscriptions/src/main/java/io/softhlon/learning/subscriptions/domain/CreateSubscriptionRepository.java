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
public interface CreateSubscriptionRepository {
    CreateSubscriptionResult execute(CreateSubscriptionRequest request);

    record CreateSubscriptionRequest(
          UUID accountId,
          String status,
          OffsetDateTime startedTime) {}

    sealed interface CreateSubscriptionResult {
        record SubscriptionPersisted(UUID uuid) implements CreateSubscriptionResult {}
        record SubscriptionPersistenceFailed(Throwable cause) implements CreateSubscriptionResult {}
    }
}

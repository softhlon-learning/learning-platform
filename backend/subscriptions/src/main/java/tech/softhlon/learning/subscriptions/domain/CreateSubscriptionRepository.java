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
public interface CreateSubscriptionRepository {
    CreateSubscriptionResult execute(CreateSubscriptionRequest request);

    sealed interface CreateSubscriptionResult {
        record SubscriptionPersisted(UUID uuid) implements CreateSubscriptionResult {}
        record SubscriptionPersistenceFailed(Throwable cause) implements CreateSubscriptionResult {}
    }

    record CreateSubscriptionRequest(
          UUID accountId,
          String status,
          OffsetDateTime startedTime) {}
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@FunctionalInterface
public interface PersistSubscriptionRepository {

    PersistSubscriptionResult execute(
          PersistSubscriptionRequest request);

    record PersistSubscriptionRequest(
          UUID id,
          UUID accountId,
          boolean active,
          OffsetDateTime activatedTime,
          OffsetDateTime deactivatedTime) {}

    sealed interface PersistSubscriptionResult {
        record SubscriptionPersisted() implements PersistSubscriptionResult {}
        record SubscriptionPersistenceFailed(Throwable cause) implements PersistSubscriptionResult {}
    }

}

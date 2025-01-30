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
public interface PersistCheckoutRepository {

    PersistCheckoutSessionResult execute(
          PersistCheckoutSessionRequest request);

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

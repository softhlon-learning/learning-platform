// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@FunctionalInterface
public interface PersistCheckoutSessionRepository {

    PersistCheckoutSessionResult execute(
          PersistCheckoutSessionRequest request);

    record PersistCheckoutSessionRequest(
          UUID accountId,
          String sessionId) {}

    sealed interface PersistCheckoutSessionResult {
        record CheckoutSessionPersisted() implements PersistCheckoutSessionResult {}
        record CheckoutSessionPersistence(Throwable cause) implements PersistCheckoutSessionResult {}
    }

}

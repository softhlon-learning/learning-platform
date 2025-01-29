// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadCheckoutRepository {

    LoadCheckoutResult execute(
          LoadCheckoutRequest request);

    record LoadCheckoutRequest(
          String sessionId,
          UUID accoountId) {}

    sealed interface LoadCheckoutResult {
        record CheckoutLoaded() implements LoadCheckoutResult {}
        record CheckoutLoadFailed(Throwable cause) implements LoadCheckoutResult {}
    }

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load checkout session repository.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadCheckoutRepository {

    /**
     * Load checkout session from repository.
     * @param sessionId Checkout session Id
     * @return
     */
    LoadCheckoutResult execute(
          String sessionId);

    /**
     * Load checkout session from repository - result.
     */
    sealed interface LoadCheckoutResult {
        record CheckoutLoaded(CheckoutSession checkoutSession) implements LoadCheckoutResult {}
        record CheckoutNotFoundInDatabase() implements LoadCheckoutResult {}
        record CheckoutLoadFailed(Throwable cause) implements LoadCheckoutResult {}
    }

    record CheckoutSession(
          UUID id,
          String sessionId,
          UUID accountId,
          OffsetDateTime expiredTime,
          OffsetDateTime completedTime) {}

}

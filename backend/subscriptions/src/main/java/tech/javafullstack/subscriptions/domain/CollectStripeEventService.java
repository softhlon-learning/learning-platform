// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import tech.javafullstack.common.hexagonal.InboundPort;

/**
 * Collect Stripe event service interface.
 */
@InboundPort
@FunctionalInterface
public interface CollectStripeEventService {

    /**
     * Collect generic Stripe event.
     * Event is not processed, just stored in repository.
     * @param sigHeader Stripe-Signature header
     * @param payload   Stripe payload
     * @return Result
     */
    Result execute(
          String payload,
          String sigHeader);

    /**
     * Collect generic Stripe event result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

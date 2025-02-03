// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Collect Stripe event service.
 */
public interface CollectStripeEventService {

    /**
     * Collect generic Stripe event.
     * Event is not processed, just stored in repository.
     * @param sigHeader Stripe-Signature header
     * @param payload   Stripe payload
     * @return Result
     */
    Result execute(
          String sigHeader,
          String payload);

    /**
     * Collect generic Stripe event result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

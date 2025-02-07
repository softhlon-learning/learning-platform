// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit subscription updated Stripe event service interface.
 */
@InboundPort
@FunctionalInterface
public interface SubmitSubscriptionUpdatedService {

    /**
     * Submit subscription updated Stripe event.
     * @param sigHeader Stripe-Signature header
     * @param payload   Stripe payload
     * @return Result
     */
    Result execute(
          String payload,
          String sigHeader);

    /**
     * Submit subscription updated Stripe event result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record IncorrectSubscription(String message) implements Result {}
        record IncorrectEventType(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

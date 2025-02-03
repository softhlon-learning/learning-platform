// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit customer created Stripe event service interface.
 */
@InboundPort
@FunctionalInterface
public interface SubmitCustomerCreatedService {

    /**
     * Submit customer created Stripe event.
     * @param sigHeader Stripe-Signature header
     * @param payload   Stripe payload
     * @return Result
     */
    Result execute(
          String sigHeader,
          String payload);

    /**
     * Submit customer created Stripe event result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record IncorrectEventType(String message) implements Result {}
        record AccountNotFound(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

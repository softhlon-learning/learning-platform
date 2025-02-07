// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

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
          String payload,
          String sigHeader);

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

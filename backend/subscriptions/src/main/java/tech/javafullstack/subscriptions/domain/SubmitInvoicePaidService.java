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
 * Submit invoice paid Stripe event service interface.
 */
@InboundPort
@FunctionalInterface
public interface SubmitInvoicePaidService {

    /**
     * Submit invoice paid Stripe event.
     * @param sigHeader Stripe-Signature header
     * @param payload   Stripe payload
     * @return Result
     */
    Result execute(
          String payload,
          String sigHeader);

    /**
     * Submit invoice paid Stripe event result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record IncorrectEventType(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

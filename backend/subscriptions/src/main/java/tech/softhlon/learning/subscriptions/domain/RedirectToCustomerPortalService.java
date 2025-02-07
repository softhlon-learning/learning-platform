// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Redirect to Stripe customer portal service interface.
 */
@InboundPort
@FunctionalInterface
public interface RedirectToCustomerPortalService {

    /**
     * Redirect to Stripe customer portal.
     * @param sigHeader Stripe-Signature header
     * @param payload   Stripe payload
     * @return Result
     */
    Result execute(
          UUID account);

    /**
     * Redirect to Stripe customer portal result.
     */
    sealed interface Result {
        record Succeeded(String url) implements Result {}
        record UnknownCustomer(String url) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

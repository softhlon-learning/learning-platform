// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Initialize Stripe checkout session service interface.
 */
@InboundPort
@FunctionalInterface
public interface InitializeCheckoutService {

    /**
     * Initialize Stripe checkout session.
     * @param acccountId Account Id
     * @param email      User's email
     * @param priceId    Stripe price id
     * @return
     */
    Result execute(
          UUID acccountId,
          String email,
          String priceId);

    /**
     * Initialize Stripe checkout session - result.
     */
    sealed interface Result {
        record Succeeded(String url) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

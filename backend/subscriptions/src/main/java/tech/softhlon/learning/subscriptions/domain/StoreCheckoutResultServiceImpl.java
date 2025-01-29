// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class StoreCheckoutResultServiceImpl implements StoreCheckoutResultService {

    private final String webhookSecret;

    public StoreCheckoutResultServiceImpl(
          @Value("${stripe.checkout-result.webhook.secret}")
          String webhookSecret) {

        this.webhookSecret = webhookSecret;
    }

    @Override
    public Result execute(Request request) {
        return null;
    }

}

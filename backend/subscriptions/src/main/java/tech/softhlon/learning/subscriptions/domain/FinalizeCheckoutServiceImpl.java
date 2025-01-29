// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService.Result.Succeeded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class FinalizeCheckoutServiceImpl implements FinalizeCheckoutService {

    private final String webhookSecret;

    public FinalizeCheckoutServiceImpl(
          @Value("${stripe.checkout-result.webhook.secret}")
          String webhookSecret) {

        this.webhookSecret = webhookSecret;
    }

    @Override
    public Result execute(Request request) {

        try {
            var event = Webhook.constructEvent(
                  request.payload(),
                  request.sigHeader(),
                  webhookSecret);

            switch (event.getType()) {
                case "checkout.session.completed":
                    log.info("service | Payment succeeded");
            }

            return new Succeeded();
        } catch (Throwable cause) {
            return new Failed(cause);
        }

    }

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.CreateCheckoutSession.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.CreateCheckoutSession.Result.Succeeded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class CreateCheckoutSessionImpl implements CreateCheckoutSession {
    private final String serviceBaseUrl;

    public CreateCheckoutSessionImpl(
          @Value("${service.base-url}") String serviceBaseUrl,
          @Value("${stripe.api-key}") String stripeApiKey) {

        this.serviceBaseUrl = serviceBaseUrl;
        Stripe.apiKey = stripeApiKey;
    }

    @Override
    public Result execute(
          Request request) {

        try {
            SessionCreateParams params = new SessionCreateParams.Builder()
                  .setSuccessUrl(serviceBaseUrl + "/home")
                  .setCancelUrl(serviceBaseUrl + "/subscribe")
                  .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                  .addLineItem(new SessionCreateParams.LineItem.Builder()
                        .setQuantity(1L)
                        .setPrice(request.priceId())
                        .build())
                  .build();

            var session =
                  Session.create(params);

            return new Succeeded(
                  session.getUrl());

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new Failed(cause);
        }

    }

}

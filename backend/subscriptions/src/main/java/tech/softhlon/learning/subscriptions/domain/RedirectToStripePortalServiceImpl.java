// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.stripe.Stripe;
import com.stripe.model.billingportal.Session;
import com.stripe.param.billingportal.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.RedirectToStripePortalService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.RedirectToStripePortalService.Result.Succeeded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class RedirectToStripePortalServiceImpl implements RedirectToStripePortalService {
    private final String serviceBaseUrl;
    private final String stripeApiKey;

    public RedirectToStripePortalServiceImpl(
          @Value("${service.base-url}") String serviceBaseUrl,
          @Value("${stripe.api-key}") String stripeApiKey) {

        this.serviceBaseUrl = serviceBaseUrl;
        this.stripeApiKey = stripeApiKey;
    }

    @Override
    public Result execute(Request request) {

        try {
            Stripe.apiKey = stripeApiKey;

            var checkoutSession = com.stripe.model.checkout.Session
                  .retrieve(request.sessionId());

            var sessionCreateParams =
                  new SessionCreateParams.Builder()
                        .setReturnUrl(serviceBaseUrl)
                        .setCustomer(checkoutSession.getCustomer())
                        .build();

            var session = Session.create(
                  sessionCreateParams);

            return new Succeeded(
                  session.getUrl());

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new Failed(cause);
        }

    }

}

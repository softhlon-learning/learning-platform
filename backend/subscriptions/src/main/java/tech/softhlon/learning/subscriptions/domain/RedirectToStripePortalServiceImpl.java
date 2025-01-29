// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

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

    public RedirectToStripePortalServiceImpl(
          @Value("${service.base-url}") String serviceBaseUrl) {

        this.serviceBaseUrl = serviceBaseUrl;
    }

    @Override
    public Result execute(Request request) {

        try {
            var sessionCreateParams =
                  new SessionCreateParams.Builder()
                        .setReturnUrl(serviceBaseUrl)
                        .setCustomer(request.customerName())
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

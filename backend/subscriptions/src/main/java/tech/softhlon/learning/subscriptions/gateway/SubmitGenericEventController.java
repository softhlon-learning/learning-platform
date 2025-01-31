// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.subscriptions.domain.CollectStripeEventService;
import tech.softhlon.learning.subscriptions.domain.CollectStripeEventService.Request;
import tech.softhlon.learning.subscriptions.domain.CollectStripeEventService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.CollectStripeEventService.Result.Succeeded;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.successCreatedBody;
import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.type;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.SUBMIT_SUBSCRIPTION_GENERIC;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
class SubmitGenericEventController {

    private final String webhookSecret;
    private final CollectStripeEventService service;
    private final HttpServletRequest httpRequest;

    public SubmitGenericEventController(
          @Value("${stripe.generic-event.webhook.secret}")
          String webhookSecret,
          CollectStripeEventService service,
          HttpServletRequest httpRequest) {

        this.webhookSecret = webhookSecret;
        this.service = service;
        this.httpRequest = httpRequest;
    }

    @PostMapping(SUBMIT_SUBSCRIPTION_GENERIC)
    ResponseEntity<?> submitSubscriptionCreated(
          @Validated @RequestBody String payload) throws SignatureVerificationException {

        var sigHeader = httpRequest.getHeader("Stripe-Signature");

        log.info("controller | Submit event: {}",
              eventType(
                    sigHeader,
                    payload));

        var result = service.execute(
              new Request(
                    sigHeader,
                    payload
              ));

        return switch (result) {
            case Succeeded() -> successCreatedBody();
            case Failed(_) -> internalServerBody(httpRequest, null);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private String eventType(
          String sigHeader,
          String payload) throws SignatureVerificationException {

        var event = Webhook.constructEvent(
              payload,
              sigHeader,
              webhookSecret);

        return type(event);

    }
}

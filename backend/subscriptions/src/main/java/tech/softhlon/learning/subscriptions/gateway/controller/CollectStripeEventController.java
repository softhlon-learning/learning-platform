// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway.controller;

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
import tech.softhlon.learning.subscriptions.domain.CollectStripeEventService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.CollectStripeEventService.Result.Succeeded;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.successCreatedBody;
import static tech.softhlon.learning.subscriptions.gateway.controller.ControllerConstants.STRIPE_SIGNATURE;
import static tech.softhlon.learning.subscriptions.gateway.controller.RestResources.SUBMIT_SUBSCRIPTION_GENERIC;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Collect Stripe event controller.
 */
@Slf4j
@RestApiAdapter
@RestController
class CollectStripeEventController {

    private final String webhookSecret;
    private final CollectStripeEventService service;
    private final HttpServletRequest httpRequest;

    public CollectStripeEventController(
          @Value("${stripe.generic-event.webhook.secret}")
          String webhookSecret,
          CollectStripeEventService service,
          HttpServletRequest httpRequest) {

        this.webhookSecret = webhookSecret;
        this.service = service;
        this.httpRequest = httpRequest;
    }

    /**
     * POST /api/v1/subscription/generic-event endpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     * @throws SignatureVerificationException
     */
    @PostMapping(SUBMIT_SUBSCRIPTION_GENERIC)
    ResponseEntity<?> collectStripeEvent(
          @Validated @RequestBody String payload) throws SignatureVerificationException {

        var sigHeader = httpRequest.getHeader(STRIPE_SIGNATURE);

        log.info("controller | request / Collect {} event",
              eventType(
                    sigHeader,
                    payload));

        var result = service.execute(
              sigHeader,
              payload
        );

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

        return Webhook.constructEvent(
              payload,
              sigHeader,
              webhookSecret).getType();

    }
}

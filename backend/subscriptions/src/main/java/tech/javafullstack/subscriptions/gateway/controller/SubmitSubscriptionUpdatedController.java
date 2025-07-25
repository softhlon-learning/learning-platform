// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.Failed;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.IncorrectEventType;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.IncorrectSubscription;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.Succeeded;

import static tech.javafullstack.common.controller.ResponseBodyHelper.*;
import static tech.javafullstack.subscriptions.gateway.controller.ControllerConstants.STRIPE_SIGNATURE;
import static tech.javafullstack.subscriptions.gateway.controller.RestResources.SUBMIT_SUBSCRIPTION_UPDATED;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit subscription updated Stripe event controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubmitSubscriptionUpdatedController {

    private final SubmitSubscriptionUpdatedService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/subscription/updated-event ednpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     */
    @PostMapping(SUBMIT_SUBSCRIPTION_UPDATED)
    ResponseEntity<?> submitSubscriptionCreated(
          @Validated @RequestBody String payload) {

        log.info("controller | request / Submit customer.subscription.updated event");

        var result = service.execute(
              payload,
              httpRequest.getHeader(STRIPE_SIGNATURE)
        );

        log.info("controller | response / Submit customer.subscription.updated event: {}", result);

        return switch (result) {
            case Succeeded succeeded -> successCreatedBody();
            case IncorrectEventType(String message) -> badRequestBody(httpRequest, message);
            case IncorrectSubscription(String message) -> badRequestBody(httpRequest, message);
            case Failed(_) -> internalServerBody(httpRequest, null);
        };

    }

}

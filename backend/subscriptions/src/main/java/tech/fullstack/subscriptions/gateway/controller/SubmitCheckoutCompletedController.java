// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.fullstack.subscriptions.domain.SubmitCheckoutCompletedService;
import tech.fullstack.subscriptions.domain.SubmitCheckoutCompletedService.Result.CheckoutNotFound;
import tech.fullstack.subscriptions.domain.SubmitCheckoutCompletedService.Result.Failed;
import tech.fullstack.subscriptions.domain.SubmitCheckoutCompletedService.Result.IncorrectEventType;
import tech.fullstack.subscriptions.domain.SubmitCheckoutCompletedService.Result.Succeeded;

import static tech.javafullstack.common.controller.ResponseBodyHelper.*;
import static tech.fullstack.subscriptions.gateway.controller.ControllerConstants.STRIPE_SIGNATURE;
import static tech.fullstack.subscriptions.gateway.controller.RestResources.SUBMIT_CHECKOUT_COMPLETED;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit checkout completed Stripe event controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubmitCheckoutCompletedController {

    private final SubmitCheckoutCompletedService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/subscription/checkout-completed-event ednpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     */
    @PostMapping(SUBMIT_CHECKOUT_COMPLETED)
    ResponseEntity<?> submitCheckoutCompleted(
          @Validated @RequestBody String payload) {

        log.info("controller | request / Submit checkout.session.completed event");

        var result = service.execute(
              payload,
              httpRequest.getHeader(STRIPE_SIGNATURE)
        );

        log.info("controller | response / Submit checkout.session.completed event: {}", result);

        return switch (result) {
            case Succeeded succeeded -> successCreatedBody();
            case CheckoutNotFound(String message) -> badRequestBody(httpRequest, message);
            case IncorrectEventType(String message) -> badRequestBody(httpRequest, message);
            case Failed(_) -> internalServerBody(httpRequest, null);
        };

    }

}

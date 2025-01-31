// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.subscriptions.domain.SubmitCheckoutCompletedService;
import tech.softhlon.learning.subscriptions.domain.SubmitCheckoutCompletedService.Result.CheckoutNotFound;
import tech.softhlon.learning.subscriptions.domain.SubmitCheckoutCompletedService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitCheckoutCompletedService.Result.IncorrectEventType;
import tech.softhlon.learning.subscriptions.domain.SubmitCheckoutCompletedService.Result.Succeeded;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.SUBMIT_CHECKOUT_COMPLETED;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubmitCheckoutCompletedController {

    private final SubmitCheckoutCompletedService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(SUBMIT_CHECKOUT_COMPLETED)
    ResponseEntity<?> submitCheckoutCompleted(
          @Validated @RequestBody String payload) {

        log.info("controller | request / Submit checkout.session.completed event");

        var result = service.execute(
              httpRequest.getHeader("Stripe-Signature"),
              payload);

        log.info("controller | response / Submit checkout.session.completed event: {}", result);

        return switch (result) {
            case Succeeded succeeded -> successCreatedBody();
            case CheckoutNotFound(String message) -> badRequestBody(httpRequest, message);
            case IncorrectEventType(String message) -> badRequestBody(httpRequest, message);
            case Failed(_) -> internalServerBody(httpRequest, null);
        };

    }

}

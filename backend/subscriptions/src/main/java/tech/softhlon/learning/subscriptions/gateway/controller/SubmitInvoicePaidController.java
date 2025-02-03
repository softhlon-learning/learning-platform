// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.subscriptions.domain.SubmitInvoicePaidService;
import tech.softhlon.learning.subscriptions.domain.SubmitInvoicePaidService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitInvoicePaidService.Result.IncorrectEventType;
import tech.softhlon.learning.subscriptions.domain.SubmitInvoicePaidService.Result.Succeeded;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.subscriptions.gateway.controller.ControllerConstants.STRIPE_SIGNATURE;
import static tech.softhlon.learning.subscriptions.gateway.controller.RestResources.SUBMIT_INVOICE_PAID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit invoice paid Stripe event controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubmitInvoicePaidController {

    private final SubmitInvoicePaidService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/subscription/invoice-paid-event ednpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     */
    @PostMapping(SUBMIT_INVOICE_PAID)
    ResponseEntity<?> submitSubscriptionCreated(
          @Validated @RequestBody String payload) {

        log.info("controller | request / Submit invoice.paid event");

        var result = service.execute(
              httpRequest.getHeader(STRIPE_SIGNATURE),
              payload);

        log.info("controller | response / Submit invoice.paid event: {}", result);

        return switch (result) {
            case Succeeded succeeded -> successCreatedBody();
            case IncorrectEventType(String message) -> badRequestBody(httpRequest, message);
            case Failed(_) -> internalServerBody(httpRequest, null);
        };

    }

}

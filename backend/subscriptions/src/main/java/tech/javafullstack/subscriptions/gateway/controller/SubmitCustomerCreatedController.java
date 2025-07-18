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
import tech.javafullstack.subscriptions.domain.SubmitCustomerCreatedService;
import tech.javafullstack.subscriptions.domain.SubmitCustomerCreatedService.Result.AccountNotFound;
import tech.javafullstack.subscriptions.domain.SubmitCustomerCreatedService.Result.Failed;
import tech.javafullstack.subscriptions.domain.SubmitCustomerCreatedService.Result.IncorrectEventType;
import tech.javafullstack.subscriptions.domain.SubmitCustomerCreatedService.Result.Succeeded;

import static tech.javafullstack.common.controller.ResponseBodyHelper.*;
import static tech.javafullstack.subscriptions.gateway.controller.ControllerConstants.STRIPE_SIGNATURE;
import static tech.javafullstack.subscriptions.gateway.controller.RestResources.SUBMIT_CUSTOMER_CREATED;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit customer created Stripe event controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubmitCustomerCreatedController {

    private final SubmitCustomerCreatedService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/subscription/customer-created-event ednpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     */
    @PostMapping(SUBMIT_CUSTOMER_CREATED)
    ResponseEntity<?> submitCustomerCreated(
          @Validated @RequestBody String payload) {

        log.info("controller | request / Submit customer.created event");

        var result = service.execute(
              payload,
              httpRequest.getHeader(STRIPE_SIGNATURE)
        );

        log.info("controller | response / Submit customer.created event: {}", result);

        return switch (result) {
            case Succeeded succeeded -> successCreatedBody();
            case IncorrectEventType(String message) -> badRequestBody(httpRequest, message);
            case AccountNotFound(String message) -> badRequestBody(httpRequest, message);
            case Failed(_) -> internalServerBody(httpRequest, null);
        };

    }

}

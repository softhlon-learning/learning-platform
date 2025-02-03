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
import tech.softhlon.learning.subscriptions.domain.SubmitCustomerCreatedService;
import tech.softhlon.learning.subscriptions.domain.SubmitCustomerCreatedService.Result.AccountNotFound;
import tech.softhlon.learning.subscriptions.domain.SubmitCustomerCreatedService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitCustomerCreatedService.Result.IncorrectEventType;
import tech.softhlon.learning.subscriptions.domain.SubmitCustomerCreatedService.Result.Succeeded;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.subscriptions.gateway.controller.RestResources.SUBMIT_CUSTOMER_CREATED;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubmitCustomerCreatedController {

    private final SubmitCustomerCreatedService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(SUBMIT_CUSTOMER_CREATED)
    ResponseEntity<?> submitCustomerCreated(
          @Validated @RequestBody String payload) {

        log.info("controller | request / Submit customer.created event");

        var result = service.execute(
              httpRequest.getHeader("Stripe-Signature"),
              payload);

        log.info("controller | response / Submit customer.created event: {}", result);

        return switch (result) {
            case Succeeded succeeded -> successCreatedBody();
            case IncorrectEventType(String message) -> badRequestBody(httpRequest, message);
            case AccountNotFound(String message) -> badRequestBody(httpRequest, message);
            case Failed(_) -> internalServerBody(httpRequest, null);
        };

    }

}

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
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionDeletedService;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionDeletedService.Result.Failed;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionDeletedService.Result.IncorrectEventType;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionDeletedService.Result.Succeeded;

import static tech.javafullstack.common.controller.ResponseBodyHelper.*;
import static tech.javafullstack.subscriptions.gateway.controller.ControllerConstants.STRIPE_SIGNATURE;
import static tech.javafullstack.subscriptions.gateway.controller.RestResources.SUBMIT_SUBSCRIPTION_DELETED;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit subscription deleted Stripe event controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubmitSubscriptionDeletedController {

    private final SubmitSubscriptionDeletedService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/subscription/deleted-event ednpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     */
    @PostMapping(SUBMIT_SUBSCRIPTION_DELETED)
    ResponseEntity<?> submitSubscriptionDeleted(
          @Validated @RequestBody String payload) {

        log.info("controller | request / Submit customer.subscription.deleted event");

        var result = service.execute(
              payload,
              httpRequest.getHeader(STRIPE_SIGNATURE)
        );

        log.info("controller | response / Submit customer.subscription.deleted event: {}", result);

        return switch (result) {
            case Succeeded succeeded -> successCreatedBody();
            case IncorrectEventType(String message) -> badRequestBody(httpRequest, message);
            case Failed(_) -> internalServerBody(httpRequest, null);
        };

    }

}

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
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService;
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService.Request;
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService.Result.CheckoutNotFound;
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService.Result.Succeeded;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.successCreatedBody;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.CHECKOUT_RESULT;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class FinalizeCheckoutController {

    private final FinalizeCheckoutService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(CHECKOUT_RESULT)
    ResponseEntity<?> finalizeCheckout(
          @Validated @RequestBody String payload) {

        log.info("controller | Finalize checkout [request]");

        var result = service.execute(
              new Request(
                    httpRequest.getHeader("Stripe-Signature"),
                    payload
              ));

        log.info("controller | Finalize checkout [response]: {}", result);

        return switch (result) {
            case CheckoutNotFound(), Failed(_) -> internalServerBody(httpRequest, null);
            case Succeeded succeeded -> successCreatedBody();
        };

    }

}

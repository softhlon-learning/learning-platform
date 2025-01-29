// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.subscriptions.domain.CreateCheckoutSession;
import tech.softhlon.learning.subscriptions.domain.CreateCheckoutSession.Request;
import tech.softhlon.learning.subscriptions.domain.CreateCheckoutSession.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.CreateCheckoutSession.Result.Succeeded;

import java.util.Map;

import static org.springframework.http.ResponseEntity.status;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.CHECKOUT_SESSION;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class CreateCheckoutSessionController {

    private static final String LOCATION = "Location";
    private final CreateCheckoutSession service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    @PostMapping(CHECKOUT_SESSION)
    ResponseEntity<?> checkoutSession(
          @RequestParam Map<String, String> body,
          HttpServletResponse response) {

        log.info("controller | Redirect to Stripe customer portal [request]");

        var accountId = authContext.accountId();
        var result = service.execute(
              new Request(
                    accountId,
                    "price_1QmYo3IdZlPlV5wEDgbXc1Js"));

        return switch (result) {
            case Succeeded(String redirectUrl) -> successBody(new CreateCheckoutResponse(redirectUrl));
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<CreateCheckoutResponse> successBody(
          CreateCheckoutResponse response) {

        return status(HttpStatus.OK)
              .body(response);

    }

    record CreateCheckoutRequest(
          String priceId) {}

    record CreateCheckoutResponse(
          String redirectUrl) {}
}

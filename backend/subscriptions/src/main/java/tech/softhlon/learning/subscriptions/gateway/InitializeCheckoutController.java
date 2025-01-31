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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.subscriptions.domain.InitializeCheckoutService;
import tech.softhlon.learning.subscriptions.domain.InitializeCheckoutService.Request;
import tech.softhlon.learning.subscriptions.domain.InitializeCheckoutService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.InitializeCheckoutService.Result.Succeeded;

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
class InitializeCheckoutController {

    private static final String LOCATION = "Location";
    private final InitializeCheckoutService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    @PostMapping(CHECKOUT_SESSION)
    ResponseEntity<?> checkoutSession(
          @Validated @RequestBody CreateCheckoutRequest request,
          HttpServletResponse response) {

        log.info("controller | request / Create Stripe checkout session");

        var accountId = authContext.accountId();
        var email = authContext.email();
        var result = service.execute(
              new Request(
                    accountId,
                    email,
                    request.priceId()));

        return switch (result) {
            case Succeeded(String redirectUrl) -> successBody(redirectUrl);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<CreateCheckoutResponse> successBody(
          String redirectUrl) {

        return status(HttpStatus.OK)
              .body(new CreateCheckoutResponse(redirectUrl));

    }

    record CreateCheckoutRequest(
          String priceId) {}

    record CreateCheckoutResponse(
          String redirectUrl) {}
}

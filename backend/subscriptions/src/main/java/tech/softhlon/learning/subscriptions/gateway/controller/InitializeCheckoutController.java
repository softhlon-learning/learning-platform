// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.gateway.controller;

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
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;
import tech.javafullstack.subscriptions.domain.InitializeCheckoutService;
import tech.javafullstack.subscriptions.domain.InitializeCheckoutService.Result.Failed;
import tech.javafullstack.subscriptions.domain.InitializeCheckoutService.Result.Succeeded;

import static org.springframework.http.ResponseEntity.status;
import static tech.javafullstack.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.javafullstack.subscriptions.gateway.controller.RestResources.CHECKOUT_SESSION;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Initialize checkout session controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class InitializeCheckoutController {

    private final InitializeCheckoutService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    /**
     * POST /api/v1/subscription/checkout-session endpoint.
     * @param request  CreateCheckoutRequest
     * @param response HttpServletResponse
     * @return ResponseEntity<?>
     */
    @PostMapping(CHECKOUT_SESSION)
    ResponseEntity<?> checkoutSession(
          @Validated @RequestBody CreateCheckoutRequest request,
          HttpServletResponse response) {

        log.info("controller | request / Create Stripe checkout session");

        var accountId = authContext.accountId();
        var email = authContext.email();
        var result = service.execute(
              accountId,
              email,
              request.priceId()
        );

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

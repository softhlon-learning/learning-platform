// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;
import tech.fullstack.subscriptions.domain.RedirectToCustomerPortalService;
import tech.fullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.Failed;
import tech.fullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.Succeeded;
import tech.fullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.UnknownCustomer;

import static org.springframework.http.ResponseEntity.status;
import static tech.javafullstack.common.controller.ResponseBodyHelper.badRequestBody;
import static tech.javafullstack.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.fullstack.subscriptions.gateway.controller.RestResources.CUSTOMER_PORTAL;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Redirect to customer portal controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class RedirectToCustomerPortalController {

    private final RedirectToCustomerPortalService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    /**
     * GET /api/v1/subscription/customer_portal endpoint.
     * @param response HttpServletResponse
     * @return ResponseEntity<?>
     */
    @GetMapping(CUSTOMER_PORTAL)
    ResponseEntity<?> redirectToStripePortal(
          HttpServletResponse response) {

        log.info("controller | request / Redirect to Stripe customer portal");
        var accountId = authContext.accountId();
        var result = service.execute(accountId);
        return switch (result) {
            case Succeeded(String redirectUrl) -> successBody(redirectUrl);
            case UnknownCustomer(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    record CreateCheckoutResponse(
          String redirectUrl) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<CreateCheckoutResponse> successBody(
          String redirectUrl) {

        return status(HttpStatus.OK)
              .body(new CreateCheckoutResponse(redirectUrl));

    }

}

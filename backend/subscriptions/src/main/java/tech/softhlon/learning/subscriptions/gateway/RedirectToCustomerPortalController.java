// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.subscriptions.domain.RedirectToCustomerPortalService;
import tech.softhlon.learning.subscriptions.domain.RedirectToCustomerPortalService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.RedirectToCustomerPortalService.Result.Succeeded;
import tech.softhlon.learning.subscriptions.domain.RedirectToCustomerPortalService.Result.UnknownCustomer;

import static org.springframework.http.ResponseEntity.status;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.CUSTOMER_PORTAL;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class RedirectToCustomerPortalController {

    private final RedirectToCustomerPortalService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

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

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<CreateCheckoutResponse> successBody(
          String redirectUrl) {

        return status(HttpStatus.OK)
              .body(new CreateCheckoutResponse(redirectUrl));

    }

    record CreateCheckoutResponse(
          String redirectUrl) {}
}

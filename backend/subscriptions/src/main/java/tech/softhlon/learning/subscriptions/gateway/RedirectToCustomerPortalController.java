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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.subscriptions.domain.RedirectToCustomerPortalService;
import tech.softhlon.learning.subscriptions.domain.RedirectToCustomerPortalService.Request;
import tech.softhlon.learning.subscriptions.domain.RedirectToCustomerPortalService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.RedirectToCustomerPortalService.Result.Succeeded;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.redirectBody;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.CUSTOMER_PORTAL;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class RedirectToCustomerPortalController {

    private static final String LOCATION = "Location";
    private final RedirectToCustomerPortalService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    @GetMapping(CUSTOMER_PORTAL)
    ResponseEntity<?> redirectToStripePortal(
          @Validated @RequestBody RedirectToPortalRequest request,
          HttpServletResponse response) {

        log.info("controller | Redirect to Stripe customer portal [request]");
        var result = service.execute(
              new Request(
                    "Name",
                    request.sessionId()
              ));

        return switch (result) {
            case Succeeded(String redirectUrl) -> redirect(response, redirectUrl);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> redirect(
          HttpServletResponse response,
          String redirectUrl) {

        addSuccessfulRedirectHeaders(
              response,
              redirectUrl);

        return redirectBody();

    }

    private void addSuccessfulRedirectHeaders(
          HttpServletResponse response,
          String redirectUrl) {

        response.setHeader(
              LOCATION,
              redirectUrl);

        response.setStatus(
              HttpStatus.FOUND.value());

    }

    record RedirectToPortalRequest(
          String sessionId) {
    }

}

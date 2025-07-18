// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import tech.javafullstack.subscriptions.domain.RedirectToCustomerPortalService;
import tech.javafullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.Failed;
import tech.javafullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.Succeeded;
import tech.javafullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.UnknownCustomer;

import static org.springframework.http.ResponseEntity.status;
import static tech.javafullstack.common.controller.ResponseBodyHelper.badRequestBody;
import static tech.javafullstack.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.javafullstack.subscriptions.gateway.controller.RestResources.CUSTOMER_PORTAL;

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
    private final ObjectMapper mapper;

    /**
     * GET /api/v1/subscription/customer_portal endpoint.
     * @param response HttpServletResponse
     * @return ResponseEntity<?>
     */
    @GetMapping(CUSTOMER_PORTAL)
    ResponseEntity<?> redirectToStripePortal(
          HttpServletResponse response) throws JsonProcessingException {

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

    private ResponseEntity<String> successBody(
          String redirectUrl) throws JsonProcessingException {

        var response = mapper.writeValueAsString(new CreateCheckoutResponse(redirectUrl));
        return status(HttpStatus.OK)
              .body(response);

    }

}

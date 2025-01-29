// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.subscriptions.domain.RedirectToStripePortalService;

import static tech.softhlon.learning.subscriptions.gateway.RestResources.CUSTOMER_PORTAL;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class RedirectToStripePortalController {
    private final RedirectToStripePortalService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    @GetMapping(CUSTOMER_PORTAL)
    ResponseEntity<?> redirectToStripePortal() {

        log.info("controller | Redirect to Stripe customer portal [request]");
        return null;

    }

}

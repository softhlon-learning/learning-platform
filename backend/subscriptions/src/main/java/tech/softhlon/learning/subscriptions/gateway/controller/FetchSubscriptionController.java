// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.subscriptions.domain.FetchFreeTrialService;
import tech.softhlon.learning.subscriptions.domain.FetchFreeTrialService.FreeTrialInfo;
import tech.softhlon.learning.subscriptions.domain.FetchFreeTrialService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.FetchFreeTrialService.Result.FreeTrialNotFoundFailed;
import tech.softhlon.learning.subscriptions.domain.FetchFreeTrialService.Result.Succeeded;

import static org.springframework.http.ResponseEntity.status;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.subscriptions.gateway.controller.RestResources.FETCH_FREE_TRIAL;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit checkout completed Stripe event controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class FetchSubscriptionController {

    private final FetchFreeTrialService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    /**
     * GET /api/v1/subscription ednpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     */
    @GetMapping(FETCH_FREE_TRIAL)
    ResponseEntity<?> fetchSubscription() {

        log.info("controller | request / Fetch subscription");

        var accountId = authContext.accountId();
        var result = service.execute(accountId);

        return switch (result) {
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
            case FreeTrialNotFoundFailed() -> badRequestBody(httpRequest, null);
            case Succeeded(FreeTrialInfo freeTrialInfo) -> successBody(freeTrialInfo);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<FreeTrialInfo> successBody(
          FreeTrialInfo freeTrialInfo) {

        return status(HttpStatus.OK)
              .body(freeTrialInfo);

    }
}

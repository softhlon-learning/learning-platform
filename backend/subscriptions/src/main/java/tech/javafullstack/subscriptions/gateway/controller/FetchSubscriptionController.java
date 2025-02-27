// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService.FreeTrialInfo;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService.Result.Failed;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService.Result.FreeTrialNotFoundFailed;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService.Result.Succeeded;

import static org.springframework.http.ResponseEntity.status;
import static tech.javafullstack.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.javafullstack.common.controller.ResponseBodyHelper.notFoundBody;
import static tech.javafullstack.subscriptions.gateway.controller.RestResources.FETCH_FREE_TRIAL;

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
    private final ObjectMapper objectMapper;

    /**
     * GET /api/v1/subscription ednpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     */
    @GetMapping(FETCH_FREE_TRIAL)
    ResponseEntity<?> fetchSubscription() throws JsonProcessingException {

        log.info("controller | request / Fetch subscription");

        var accountId = authContext.accountId();
        var result = service.execute(accountId);

        return switch (result) {
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
            case FreeTrialNotFoundFailed() -> notFoundBody();
            case Succeeded(FreeTrialInfo freeTrialInfo) -> successBody(freeTrialInfo);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<String> successBody(
          FreeTrialInfo freeTrialInfo) throws JsonProcessingException {

        return status(HttpStatus.OK)
              .body(objectMapper.writeValueAsString(freeTrialInfo));
    }
}

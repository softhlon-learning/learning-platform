// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService.FreeTrialInfo;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.status;
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

    @Autowired
    private Gson gson;

    private final FetchFreeTrialService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    private static final LoadFreeTrialRepository.FreeTrial freeTrial =
          new LoadFreeTrialRepository.FreeTrial(UUID.randomUUID(), UUID.randomUUID(), OffsetDateTime.now());



    /**
     * GET /api/v1/subscription ednpoint.
     * @param payload Stripe event payload
     * @return ResponseEntity<?>
     */
    @GetMapping(FETCH_FREE_TRIAL)
    ResponseEntity<?> fetchSubscription() throws JsonProcessingException {

        log.info("controller | request / Fetch subscription");

        var accountId = authContext.accountId();
        var result = freeTrial(freeTrial);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        String jsonResult = mapper.writeValueAsString(freeTrial(freeTrial));

        if (true) {
            return status(HttpStatus.OK)
                  .body(jsonResult);
        } else {
            return null;
        }

//        return switch (result) {
//            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
//            case FreeTrialNotFoundFailed() -> notFoundBody();
//            case Succeeded(FreeTrialInfo freeTrialInfo) -> successBody(freeTrialInfo);
//        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<FreeTrialInfo> successBody(
          FreeTrialInfo freeTrialInfo) {

        return status(HttpStatus.OK)
              .body(freeTrialInfo);

    }

    private FreeTrialInfo freeTrial(
          LoadFreeTrialRepository.FreeTrial freeTrial) {

        Duration duration = Duration.between(
              OffsetDateTime.now(),
              freeTrial.expireAt());

        return new FreeTrialInfo(
              duration.isNegative()
                    ? true
                    : false,
              freeTrial.expireAt(),
              timeLeftString(duration)
        );

    }

    private String timeLeftString(Duration duration) {
        if (duration.isNegative()) {
            return null; // Or handle negative durations as needed
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60; // Get remaining minutes after hours

        StringBuilder timeLeft = new StringBuilder();

        // Handle hours
        switch ((int) hours) {
            case 0:
                // Only show hours if non-zero
                break;
            case 1:
                timeLeft.append("1 hour ");
                break;
            default:
                if (hours > 1) {
                    timeLeft.append(hours).append(" hours ");
                }
        }

        // Handle minutes
        switch ((int) minutes) {
            case 0:
                // Only show minutes if non-zero and hours is zero
                if (hours == 0) {
                    timeLeft.append("any moment");
                }
                break;
            case 1:
                timeLeft.append("1 minute");
                break;
            default:
                if (minutes > 1) {
                    timeLeft.append(minutes).append(" minutes");
                }
        }

        return timeLeft.toString().trim();
    }

}

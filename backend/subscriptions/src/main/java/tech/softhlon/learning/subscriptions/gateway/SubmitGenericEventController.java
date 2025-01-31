// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.successCreatedBody;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.SUBMIT_SUBSCRIPTION_GENERIC;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubmitGenericEventController {

    private final HttpServletRequest httpRequest;

    @PostMapping(SUBMIT_SUBSCRIPTION_GENERIC)
    ResponseEntity<?> submitSubscriptionCreated(
          @Validated @RequestBody String payload) {

        log.info("controller | Submit event: {}", payload);
        return successCreatedBody();

    }

}

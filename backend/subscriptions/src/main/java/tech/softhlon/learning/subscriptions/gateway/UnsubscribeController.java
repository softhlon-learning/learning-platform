// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import lombok.extern.slf4j.Slf4j;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.subscriptions.domain.UnsubscribeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.subscriptions.domain.UnsubscribeService.Result.*;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.UNSUBSCRIBE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UnsubscribeController {
    private final UnsubscribeService service;
    private final HttpServletRequest httpRequest;

    @DeleteMapping(UNSUBSCRIBE)
    ResponseEntity<?> unsubscribe(@Validated @RequestBody UnsubscribeService.Request request) {
        log.info("Requested, body: {}", request);
        return switch (service.execute(request)) {
            case Succeeded() -> successAcceptedBody();
            case AccountNotSubscribedFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

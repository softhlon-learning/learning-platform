// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import lombok.extern.slf4j.Slf4j;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.subscriptions.domain.SubscribeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.subscriptions.domain.SubscribeService.Request;
import static tech.softhlon.learning.subscriptions.domain.SubscribeService.Result.*;
import static tech.softhlon.learning.subscriptions.gateway.RestResources.SUBSCRIBE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SubscribeController {
    private final SubscribeService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    @PostMapping(SUBSCRIBE)
    ResponseEntity<?> subscribe() {
        log.info("Requested, body: {}");
        return switch (service.execute(new Request(authContext.accountId()))) {
            case Succeeded() -> successCreatedBody();
            case AccountAlreadySubscribedFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

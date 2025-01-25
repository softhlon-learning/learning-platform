// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.subscriptions.domain.SubscribeService;
import tech.softhlon.learning.subscriptions.domain.SubscribeService.Request;
import tech.softhlon.learning.subscriptions.domain.SubscribeService.Result.AccountAlreadySubscribedFailed;
import tech.softhlon.learning.subscriptions.domain.SubscribeService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubscribeService.Result.Succeeded;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.common.text.IdPrinter.printShort;
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

    /**
     * POST /api/v1/subscription.
     */
    @PostMapping(SUBSCRIBE)
    ResponseEntity<?> subscribe() {

        var accountId = authContext.accountId();
        log.info("Requested, accountId: {}",
              printShort(accountId));

        return switch (service.execute(new Request(accountId))) {
            case Succeeded() -> successCreatedBody();
            case AccountAlreadySubscribedFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

}

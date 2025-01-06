// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.gateway;

import io.softhlon.learning.subscriptions.domain.SubscribeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static io.softhlon.learning.subscriptions.domain.SubscribeService.Result.*;
import static io.softhlon.learning.subscriptions.gateway.RestResources.SUBSCRIBE;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class SubscribeController {
    private final SubscribeService subscribeService;
    private final HttpServletRequest servletRequest;

    @PostMapping(SUBSCRIBE)
    ResponseEntity<?> subscribe(@Validated @RequestBody SubscribeService.Request request) {
        return switch (subscribeService.subscribe(request)) {
            case Success() -> successBody();
            case AccountAlreadySubscribed(String message) -> badRequestBody(servletRequest, message);
            case InternalFailure(Throwable cause) -> internalServerBody(servletRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private static ResponseEntity<SubscribeService.Result> successBody() {
        return status(HttpStatus.CREATED).build();
    }
}

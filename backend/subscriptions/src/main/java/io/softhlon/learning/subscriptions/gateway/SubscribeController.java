// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.gateway;

import io.softhlon.learning.subscriptions.domain.SubscribeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static io.softhlon.learning.subscriptions.domain.SubscribeService.Result.*;
import static io.softhlon.learning.subscriptions.domain.SubscribeService.Request;
import static io.softhlon.learning.subscriptions.gateway.RestResources.SUBSCRIBE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class SubscribeController {
    private final SubscribeService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(SUBSCRIBE)
    ResponseEntity<?> subscribe() {
        return switch (service.subscribe(new Request(UUID.randomUUID()))) {
            case Success() -> successCreatedBody();
            case AccountAlreadySubscribed(String message) -> badRequestBody(httpRequest, message);
            case InternalFailure(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

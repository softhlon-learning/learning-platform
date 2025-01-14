// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.SignOutService;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import static tech.softhlon.learning.accounts.domain.SignOutService.Result.Failed;
import static tech.softhlon.learning.accounts.domain.SignOutService.Result.Succeeded;
import static tech.softhlon.learning.accounts.gateway.RestResources.SIGN_OUT;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.successCreatedBody;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SignOutController {
    private final SignOutService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(SIGN_OUT)
    ResponseEntity<?> signOut(@Validated @RequestBody SignOutService.Request request) {
        log.info("Requested, body: {}", request);
        var result = service.execute(request);
        return switch (result) {
            case Succeeded() -> successCreatedBody();
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

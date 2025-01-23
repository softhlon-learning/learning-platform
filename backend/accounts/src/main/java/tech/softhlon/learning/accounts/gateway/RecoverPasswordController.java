// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Request;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Result.EmailNotFoundFailed;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Result.Failed;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Result.Succeeded;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import static tech.softhlon.learning.accounts.gateway.RestResources.PASSWORD_RECOVERY;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class RecoverPasswordController {
    private final RecoverPasswordService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/password-recovery endpoint.
     */
    @PostMapping(PASSWORD_RECOVERY)
    ResponseEntity<?> recoverPassword(
          @Validated @RequestBody RecoverPasswordRequest request,
          HttpServletResponse response) {
        log.info("Requested, email: {}", request.email());
        var result = service.execute(new Request(request.email()));
        return switch (result) {
            case Succeeded() -> successCreatedBody();
            case EmailNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }

    record RecoverPasswordRequest(String email) {}
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.accounts.domain.ResetPasswordService;
import tech.javafullstack.accounts.domain.ResetPasswordService.Result.EmailNotFoundFailed;
import tech.javafullstack.accounts.domain.ResetPasswordService.Result.EmailPolicyFailed;
import tech.javafullstack.accounts.domain.ResetPasswordService.Result.Failed;
import tech.javafullstack.accounts.domain.ResetPasswordService.Result.Succeeded;
import tech.javafullstack.common.hexagonal.RestApiAdapter;

import static tech.javafullstack.accounts.gateway.controller.RestResources.RESET_PASSWORD;
import static tech.javafullstack.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Reset password controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class ResetPasswordController {

    private final ResetPasswordService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/reset-password endpoint.
     * @param request  ResetPasswordRequest
     * @param response HttpServletResponse
     * @return
     */
    @PostMapping(RESET_PASSWORD)
    ResponseEntity<?> resetPassword(
          @Validated @RequestBody ResetPasswordRequest request,
          HttpServletResponse response) {

        log.info("controller | request / Reset password, email: {}",
              request.email());

        var result = service.execute(request.email());
        return switch (result) {
            case Succeeded() -> successCreatedBody();
            case EmailPolicyFailed(String message) -> badRequestBody(httpRequest, message);
            case EmailNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private record ResetPasswordRequest(
          String email) {}

}

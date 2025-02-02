// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
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
import tech.softhlon.learning.accounts.domain.ResetPasswordService;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.EmailNotFoundFailed;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.EmailPolicyFailed;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.Failed;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.Succeeded;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import static tech.softhlon.learning.accounts.gateway.RestResources.RESET_PASSWORD;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class ResetPasswordController {

    private final ResetPasswordService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/reset-password endpoint.
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

    record ResetPasswordRequest(
          String email) {}

}

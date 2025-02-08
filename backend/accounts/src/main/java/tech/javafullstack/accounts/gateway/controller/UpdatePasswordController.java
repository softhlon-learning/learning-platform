// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
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
import tech.javafullstack.accounts.domain.UpdatePasswordService;
import tech.javafullstack.accounts.domain.UpdatePasswordService.Result.*;
import tech.javafullstack.common.hexagonal.RestApiAdapter;

import static tech.javafullstack.accounts.gateway.controller.RestResources.UPDATE_PASSWORD;
import static tech.javafullstack.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Update password controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UpdatePasswordController {

    private final UpdatePasswordService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/update-password endpoint.
     * @param request  PasswordUpdate
     * @param response HttpServletResponse
     * @return ResponseEntity<?>
     */
    @PostMapping(UPDATE_PASSWORD)
    ResponseEntity<?> updatePassword(
          @Validated @RequestBody PasswordUpdate request,
          HttpServletResponse response) {

        log.info("controller | request / Update password, {}",
              request);

        var result = service.execute(
              request.token(),
              request.password
        );

        return switch (result) {
            case Succeeded(_) -> successCreatedBody();
            case PasswordPolicyFailed(String message) -> badRequestBody(httpRequest, message);
            case ExpiredTokenFailed(String message) -> badRequestBody(httpRequest, message);
            case InvalidTokenFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    record PasswordUpdate(
          String token,
          String password) {

        @Override
        public String toString() {
            return """
                  [token: %s, password: ************]"""
                  .formatted(token);
        }

    }

}

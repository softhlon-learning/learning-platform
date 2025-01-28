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
import tech.softhlon.learning.accounts.domain.UpdatePasswordService;
import tech.softhlon.learning.accounts.domain.UpdatePasswordService.Request;
import tech.softhlon.learning.accounts.domain.UpdatePasswordService.Result.*;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import static tech.softhlon.learning.accounts.gateway.RestResources.UPDATE_PASSWORD;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UpdatePasswordController {

    private final UpdatePasswordService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/update-password endpoint.
     */
    @PostMapping(UPDATE_PASSWORD)
    ResponseEntity<?> updatePasswrd(@Validated @RequestBody PasswordUpdate request, HttpServletResponse response) {

        log.info("controller | Update password [request],{}",
              request);

        var result = service.execute(
              new Request(
                    request.token(),
                    request.password));

        return switch (result) {
            case Succeeded succeeded -> successCreatedBody();
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

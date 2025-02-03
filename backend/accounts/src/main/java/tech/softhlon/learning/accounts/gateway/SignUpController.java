// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.SignUpService;
import tech.softhlon.learning.accounts.domain.SignUpService.Result.*;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.status;
import static tech.softhlon.learning.accounts.gateway.RestResources.SIGN_UP;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class SignUpController {

    private final SignUpService service;
    private final AuthCookiesService authCookiesService;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/sign-up endpoint.
     */
    @PostMapping(SIGN_UP)
    ResponseEntity<?> signUp(
          Request request,
          HttpServletResponse response) {

        log.info("controller | request / Sign up, {}",
              request);

        var result = service.execute(
              request.name(),
              request.email(),
              request.password());

        return switch (result) {
            case Succeeded(UUID id, String token) -> success(response, token);
            case AccountAlreadyExistsFailed(String message) -> badRequestBody(httpRequest, message);
            case AccountIsDeletedFailed(String message) -> badRequestBody(httpRequest, message);
            case NamePolicyFailed(String message) -> badRequestBody(httpRequest, message);
            case EmailPolicyFailed(String message) -> badRequestBody(httpRequest, message);
            case PasswordPolicyFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    record Request(
          String name,
          String email,
          String password) {}

    record Response(
          UUID accountId) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<Response> successBody(
          UUID id) {

        return status(HttpStatus.CREATED)
              .body(new Response(id));

    }

    private ResponseEntity<?> success(
          HttpServletResponse response,
          String token) {

        authCookiesService.addAuthSucceededCookies(
              response,
              token);

        return successCreatedBody();

    }

}

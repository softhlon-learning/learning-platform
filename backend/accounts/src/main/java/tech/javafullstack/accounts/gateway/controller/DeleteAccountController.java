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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.accounts.domain.DeleteAccountService;
import tech.javafullstack.accounts.domain.DeleteAccountService.Result.AccountIsAlreadyDeletedFailed;
import tech.javafullstack.accounts.domain.DeleteAccountService.Result.AccountNotFoundFailed;
import tech.javafullstack.accounts.domain.DeleteAccountService.Result.Failed;
import tech.javafullstack.accounts.domain.DeleteAccountService.Result.Succeeded;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;

import static tech.javafullstack.accounts.gateway.controller.RestResources.ACCOUNT;
import static tech.javafullstack.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Delete account controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class DeleteAccountController {

    private final DeleteAccountService deleteAccountService;
    private final AuthCookiesService authCookiesService;
    private final AuthenticationContext authContext;
    private final HttpServletRequest httpRequest;

    /**
     * DELETE /api/v1/account endpoint.
     * @param response HttpServletResponse
     * @return ResponseEntity<?>
     */
    @DeleteMapping(path = ACCOUNT)
    ResponseEntity<?> delete(
          HttpServletResponse response) {

        log.info("controller | request / Delete account");
        var accountId = authContext.accountId();
        var result = deleteAccountService.execute(accountId);

        return switch (result) {
            case Succeeded() -> successResponse(response);
            case AccountIsAlreadyDeletedFailed(String message) -> badRequestBody(httpRequest, message);
            case AccountNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> successResponse(
          HttpServletResponse response) {

        authCookiesService.resetAuthCookies(response);
        return successAcceptedBody();

    }

}

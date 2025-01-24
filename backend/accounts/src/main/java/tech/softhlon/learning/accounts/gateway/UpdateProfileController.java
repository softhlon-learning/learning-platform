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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.UpdateProfileService;
import tech.softhlon.learning.accounts.domain.UpdateProfileService.Request;
import tech.softhlon.learning.accounts.domain.UpdateProfileService.Result.AccountNotFoundFailed;
import tech.softhlon.learning.accounts.domain.UpdateProfileService.Result.Failed;
import tech.softhlon.learning.accounts.domain.UpdateProfileService.Result.Succeeded;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;

import static tech.softhlon.learning.accounts.gateway.RestResources.PROFILE;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UpdateProfileController {

    private final UpdateProfileService service;
    private final AuthenticationContext authContext;
    private final HttpServletRequest httpRequest;

    /**
     * PUT /api/v1/account/profile endpoint.
     */
    @PutMapping(PROFILE)
    ResponseEntity<?> signIn(
          @Validated @RequestBody Profile request,
          HttpServletResponse response) {

        var accountId = authContext.accountId();

        log.info("Requested, accountId: {}, body: {}",
              accountId,
              request);

        var result = service.execute(
              new Request(
                    accountId,
                    request.name()));

        return switch (result) {
            case AccountNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
            case Succeeded succeeded -> successOkBody();
        };

    }

    record Profile(String name) {}

}

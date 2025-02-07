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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.accounts.domain.UpdateProfileService;
import tech.javafullstack.accounts.domain.UpdateProfileService.Result.AccountNotFoundFailed;
import tech.javafullstack.accounts.domain.UpdateProfileService.Result.Failed;
import tech.javafullstack.accounts.domain.UpdateProfileService.Result.Succeeded;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;

import static tech.javafullstack.accounts.gateway.controller.RestResources.PROFILE;
import static tech.javafullstack.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Update profile controller.
 */
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
     * @param request  Profile
     * @param response HttpServletResponse
     * @return ResponseEntity<?>
     */
    @PutMapping(PROFILE)
    ResponseEntity<?> updateProfile(
          @Validated @RequestBody Profile request,
          HttpServletResponse response) {

        log.info("controller | request / Update profile, {}",
              request);

        var accountId = authContext.accountId();
        var result = service.execute(
              accountId,
              request.name()
        );

        return switch (result) {
            case AccountNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
            case Succeeded succeeded -> successOkBody();
        };

    }

    record Profile(String name) {

        @Override
        public String toString() {
            return """
                  [name: %s]"""
                  .formatted(name);
        }

    }

}

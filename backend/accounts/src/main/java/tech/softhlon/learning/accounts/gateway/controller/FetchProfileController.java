// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.FetchProfileService;
import tech.softhlon.learning.accounts.domain.FetchProfileService.ProfileView;
import tech.softhlon.learning.accounts.domain.FetchProfileService.Result.Failed;
import tech.softhlon.learning.accounts.domain.FetchProfileService.Result.ProfileNotFoundFailed;
import tech.softhlon.learning.accounts.domain.FetchProfileService.Result.Succeeded;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;

import static org.springframework.http.ResponseEntity.status;
import static tech.softhlon.learning.accounts.gateway.controller.RestResources.PROFILE;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Fetch profile controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class FetchProfileController {

    private final FetchProfileService service;
    private final AuthenticationContext authContext;
    private final HttpServletRequest httpRequest;

    /**
     * GET /api/v1/account/profile endpoint.
     * @return ResponseEntity<?>
     */
    @GetMapping(PROFILE)
    ResponseEntity<?> getProfile() {

        log.info("controller | request / Fetch profile");

        var accountId = authContext.accountId();
        var result = service.execute(accountId);

        return switch (result) {
            case Succeeded(ProfileView profileView) -> successBody(profileView);
            case ProfileNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    record Profile(
          String email,
          String name) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> successBody(
          FetchProfileService.ProfileView profileView) {

        return status(HttpStatus.OK)
              .body(profile(profileView));

    }

    private Profile profile(
          ProfileView profileView) {

        return new Profile(
              profileView.email(),
              profileView.name());

    }

}

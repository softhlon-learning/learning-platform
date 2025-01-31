// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.accounts.domain.GetProfileService;
import tech.softhlon.learning.accounts.domain.GetProfileService.ProfileView;
import tech.softhlon.learning.accounts.domain.GetProfileService.Result.Failed;
import tech.softhlon.learning.accounts.domain.GetProfileService.Result.ProfileNotFoundFailed;
import tech.softhlon.learning.accounts.domain.GetProfileService.Result.Succeeded;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;

import static org.springframework.http.ResponseEntity.status;
import static tech.softhlon.learning.accounts.gateway.RestResources.PROFILE;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class GetProfileController {

    private final GetProfileService service;
    private final AuthenticationContext authContext;
    private final HttpServletRequest httpRequest;

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

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> successBody(
          GetProfileService.ProfileView profileView) {

        return status(HttpStatus.OK)
              .body(profile(profileView));

    }

    private Profile profile(
          ProfileView profileView) {

        return new Profile(
              profileView.email(),
              profileView.name());

    }

    record Profile(
          String email,
          String name) {}

}

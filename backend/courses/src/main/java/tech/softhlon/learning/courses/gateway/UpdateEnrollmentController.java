// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService.Request;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService.Result.AccountNotEligibleFailed;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService.Result.EnrollmentNotFoundFailed;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService.Result.Failed;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService.Result.Succeeded;

import java.util.UUID;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.courses.gateway.RestResources.UPDATE_COURSE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UpdateEnrollmentController {

    private final UpdateEnrollmentService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    /**
     * PATCH /api/v1/course/{courseId}.
     */
    @PatchMapping(UPDATE_COURSE)
    ResponseEntity<?> updateCourse(
          @PathVariable("courseId") UUID courseId,
          @Validated @RequestBody UpdateEnrollmentRequest request) {

        var accountId = authContext.accountId();
        log.info("Requested, accountId: {}, courseId: {}",
              accountId,
              courseId);

        var result = service.execute(
              prepareRequest(courseId, request));

        return switch (result) {
            case Succeeded() -> successOkBody();
            case EnrollmentNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case AccountNotEligibleFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Request prepareRequest(
          UUID courseId,
          UpdateEnrollmentRequest updateEnrollmentRequest) {

        return new Request(
              authContext.accountId(),
              courseId,
              updateEnrollmentRequest.content());

    }

    record UpdateEnrollmentRequest(String content) {}

}

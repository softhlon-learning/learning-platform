// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.common.hexagonal.RestApiAdapter;
import io.softhlon.learning.common.security.AuthenticationContext;
import io.softhlon.learning.courses.domain.UpdateEnrollmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static io.softhlon.learning.courses.domain.UpdateEnrollmentService.Request;
import static io.softhlon.learning.courses.domain.UpdateEnrollmentService.Result.*;
import static io.softhlon.learning.courses.gateway.RestResources.UPDATE_COURSE;


// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UpdateEnrollmentController {
    private final UpdateEnrollmentService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    @PatchMapping(UPDATE_COURSE)
    ResponseEntity<?> updateCourse(
          @PathVariable("courseId") UUID courseId,
          @Validated @RequestBody UpdateEnrollmentRequest courseRequest) {
        var result = service.execute(prepareRequest(courseId, courseRequest));
        return switch (result) {
            case Succeeded() -> successOkBody();
            case EnrollmentNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case AccountNotEligibleFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }

    record UpdateEnrollmentRequest(String content) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Request prepareRequest(
          UUID courseId, UpdateEnrollmentRequest updateEnrollmentRequest) {
        return new Request(
              authContext.accountId(),
              courseId,
              updateEnrollmentRequest.content());
    }
}

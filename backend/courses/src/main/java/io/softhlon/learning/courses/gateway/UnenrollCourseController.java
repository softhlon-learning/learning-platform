// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.common.hexagonal.RestApiAdapter;
import io.softhlon.learning.common.security.AuthenticationContext;
import io.softhlon.learning.courses.domain.UnenrollCourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static io.softhlon.learning.courses.domain.UnenrollCourseService.Request;
import static io.softhlon.learning.courses.domain.UnenrollCourseService.Result.*;
import static io.softhlon.learning.courses.gateway.RestResources.UNENROLL_COURSE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UnenrollCourseController {
    private final UnenrollCourseService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    @DeleteMapping(UNENROLL_COURSE)
    ResponseEntity<?> unenrollCourse(@PathVariable("courseId") UUID courseId) {
        return switch (service.unenroll(prepareRequest(courseId))) {
            case Succeeded() -> successAcceptedBody();
            case EnrollmentNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Request prepareRequest(UUID courseId) {
        return new Request(authContext.accountId(), courseId);
    }
}

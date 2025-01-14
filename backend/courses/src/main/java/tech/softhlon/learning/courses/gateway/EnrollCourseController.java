// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

import lombok.extern.slf4j.Slf4j;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.courses.domain.EnrollCourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.courses.domain.EnrollCourseService.Request;
import static tech.softhlon.learning.courses.domain.EnrollCourseService.Result.*;
import static tech.softhlon.learning.courses.gateway.RestResources.ENROLL_COURSE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class EnrollCourseController {
    private final EnrollCourseService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    @PostMapping(ENROLL_COURSE)
    ResponseEntity<?> enrollCourse(@PathVariable("courseId") UUID courseId) {
        log.info("Requested, courseId: {}", courseId);
        return switch (service.execute(prepareRequest(courseId))) {
            case Succeeded() -> successCreatedBody();
            case AccountNotSubscribedFailed(String message) -> badRequestBody(httpRequest, message);
            case CourseNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
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

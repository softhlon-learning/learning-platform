// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;
import tech.javafullstack.courses.domain.EnrollCourseService;
import tech.javafullstack.courses.domain.EnrollCourseService.Result.AccountNotSubscribedFailed;
import tech.javafullstack.courses.domain.EnrollCourseService.Result.CourseNotFoundFailed;
import tech.javafullstack.courses.domain.EnrollCourseService.Result.Failed;
import tech.javafullstack.courses.domain.EnrollCourseService.Result.Succeeded;

import java.util.UUID;

import static tech.javafullstack.common.controller.ResponseBodyHelper.*;
import static tech.javafullstack.common.text.IdPrinter.printShort;
import static tech.javafullstack.courses.gateway.controller.RestResources.ENROLL_COURSE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Enroll course controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class EnrollCourseController {

    private final EnrollCourseService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    /**
     * POST /api/v1/course/{courseId}/enrollment endpoint.
     * @param courseId Course Id
     * @return ResponseEntity<?>
     */
    @PostMapping(ENROLL_COURSE)
    ResponseEntity<?> enrollCourse(
          @PathVariable("courseId") UUID courseId) {

        var accountId = authContext.accountId();
        log.info("controller | request / Enroll course, courseId: {}",
              printShort(courseId));

        var result = service.execute(
              accountId,
              courseId);

        return switch (result) {
            case Succeeded() -> successCreatedBody();
            case AccountNotSubscribedFailed(String message) -> badRequestBody(httpRequest, message);
            case CourseNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

}

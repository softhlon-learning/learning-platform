// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;
import tech.javafullstack.courses.domain.UnenrollCourseService;
import tech.javafullstack.courses.domain.UnenrollCourseService.Result.EnrollmentNotFoundFailed;
import tech.javafullstack.courses.domain.UnenrollCourseService.Result.Failed;
import tech.javafullstack.courses.domain.UnenrollCourseService.Result.Succeeded;

import java.util.UUID;

import static tech.javafullstack.common.controller.ResponseBodyHelper.*;
import static tech.javafullstack.common.text.IdPrinter.printShort;
import static tech.javafullstack.courses.gateway.controller.RestResources.UNENROLL_COURSE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Unenroll course controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UnenrollCourseController {

    private final UnenrollCourseService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    /**
     * DEELTE /api/v1/course/{courseId}/enrollment endpoint.
     * @param courseId Course Id
     * @return ResponseEntity<?>
     */
    @DeleteMapping(UNENROLL_COURSE)
    ResponseEntity<?> unenrollCourse(
          @PathVariable("courseId") UUID courseId) {

        var accountId = authContext.accountId();
        log.info("controller | request / Unenroll course, courseId: {}",
              printShort(courseId));

        return switch (service.execute(accountId, courseId)) {
            case Succeeded() -> successAcceptedBody();
            case EnrollmentNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.courses.domain.UnenrollCourseService;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.EnrollmentNotFoundFailed;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.Failed;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.Succeeded;

import java.util.UUID;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.common.text.IdPrinter.printShort;
import static tech.softhlon.learning.courses.gateway.RestResources.UNENROLL_COURSE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UnenrollCourseController {

    private final UnenrollCourseService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    /**
     * DEELTE /api/v1/course/{courseId}/enrollment.
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

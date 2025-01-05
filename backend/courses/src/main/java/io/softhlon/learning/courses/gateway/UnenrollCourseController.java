// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.courses.domain.EnrollCourseService;
import io.softhlon.learning.courses.domain.UnenrollCourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static io.softhlon.learning.courses.domain.UnenrollCourseService.Result.*;
import static io.softhlon.learning.courses.gateway.RestResources.UNENROLL_COURSE;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class UnenrollCourseController {
    private final UnenrollCourseService unenrollCourseService;
    private final HttpServletRequest servletRequest;

    @PostMapping(UNENROLL_COURSE)
    ResponseEntity<?> enrollCourse(@Validated @RequestBody UnenrollCourseService.Request request) {
        return switch (unenrollCourseService.unenroll(request)) {
            case Success() -> successBody();
            case CourseNotFound(String message) -> badRequestBody(servletRequest, message);
            case AccountNotEnrolled(String message) -> badRequestBody(servletRequest, message);
            case InternalFailure(Throwable cause) -> internalServerBody(servletRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private static ResponseEntity<EnrollCourseService.Result> successBody() {
        return status(HttpStatus.CREATED).build();
    }
}

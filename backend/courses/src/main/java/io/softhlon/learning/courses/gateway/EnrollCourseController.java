// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.courses.domain.EnrollCourseService;
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
import static io.softhlon.learning.courses.domain.EnrollCourseService.Result.*;
import static io.softhlon.learning.courses.gateway.RestResources.ENROLL_COURSE;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class EnrollCourseController {
    private final EnrollCourseService enrollCourseService;
    private final HttpServletRequest servletRequest;

    @PostMapping(ENROLL_COURSE)
    ResponseEntity<?> enrollCourse(@Validated @RequestBody EnrollCourseService.Request request) {
        return switch (enrollCourseService.enroll(request)) {
            case Success() -> successBody();
            case AccountNotSubscribed(String message) -> badRequestBody(servletRequest, message);
            case CourseNotFound(String message) -> badRequestBody(servletRequest, message);
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

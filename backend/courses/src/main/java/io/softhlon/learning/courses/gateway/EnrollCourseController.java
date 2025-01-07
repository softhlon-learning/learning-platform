// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.courses.domain.EnrollCourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static io.softhlon.learning.courses.domain.EnrollCourseService.Result.*;
import static io.softhlon.learning.courses.gateway.RestResources.ENROLL_COURSE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class EnrollCourseController {
    private final EnrollCourseService service;
    private final HttpServletRequest httpRequest;

    @PostMapping(ENROLL_COURSE)
    ResponseEntity<?> enrollCourse(@Validated @RequestBody EnrollCourseService.Request request) {
        return switch (service.enroll(request)) {
            case Success() -> successCreatedBody();
            case AccountNotSubscribed(String message) -> badRequestBody(httpRequest, message);
            case CourseNotFound(String message) -> badRequestBody(httpRequest, message);
            case InternalFailure(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

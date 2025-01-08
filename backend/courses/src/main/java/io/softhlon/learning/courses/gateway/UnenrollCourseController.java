// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.common.hexagonal.RestApiAdapter;
import io.softhlon.learning.courses.domain.UnenrollCourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.*;
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

    @DeleteMapping(UNENROLL_COURSE)
    ResponseEntity<?> unenrollCourse(@Validated @RequestBody UnenrollCourseService.Request request) {
        return switch (service.unenroll(request)) {
            case Succeeded() -> successCreatedBody();
            case CourseNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case AccountNotEnrolledFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

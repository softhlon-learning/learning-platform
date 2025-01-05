// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.courses.domain.UpdateEnrolledCourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static io.softhlon.learning.courses.domain.UpdateEnrolledCourseService.Result.*;
import static io.softhlon.learning.courses.gateway.RestResources.UPDATE_COURSE;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class UpdateCourseController {
    private final UpdateEnrolledCourseService updateEnrolledCourseService;
    private final HttpServletRequest servletRequest;

    @PutMapping(UPDATE_COURSE)
    ResponseEntity<?> enrollCourse(@Validated @RequestBody UpdateEnrolledCourseService.Request request) {
        var result = updateEnrolledCourseService.updateCourse(request);
        return switch (result) {
            case Success() -> successBody();
            case CourseNotFound(String message) -> badRequestBody(servletRequest, message);
            case AccountNotEligible(String message) -> badRequestBody(servletRequest, message);
            case InternalFailure(Throwable cause) -> internalServerBody(servletRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private static ResponseEntity successBody() {
        return status(HttpStatus.OK).build();
    }
}

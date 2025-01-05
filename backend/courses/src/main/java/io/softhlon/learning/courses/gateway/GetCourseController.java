// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.courses.domain.GetCourseDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static io.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static io.softhlon.learning.courses.domain.GetCourseDetailsService.CourseDetails;
import static io.softhlon.learning.courses.domain.GetCourseDetailsService.Result.*;
import static io.softhlon.learning.courses.gateway.RestResources.GET_COURSE;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class GetCourseController {
    private final GetCourseDetailsService getCourseDetailsService;
    private final HttpServletRequest servletRequest;

    @GetMapping(GET_COURSE)
    ResponseEntity<?> enrollCourse(@Validated @RequestBody GetCourseDetailsService.Request request) {
        var result = getCourseDetailsService.getDetails(request);
        return switch (result) {
            case Success(CourseDetails courseDetails) -> successBody(courseDetails);
            case CourseNotFound(String message) -> badRequestBody(servletRequest, message);
            case InternalFailure(Throwable cause) -> internalServerBody(servletRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private static ResponseEntity<CourseDetails> successBody(CourseDetails course) {
        return status(HttpStatus.OK).body(course);
    }
}

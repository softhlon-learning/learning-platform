// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.courses.domain.GetCourseDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.badRequestBody;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.courses.domain.GetCourseDetailsService.CourseDetails;
import static tech.softhlon.learning.courses.domain.GetCourseDetailsService.Result.*;
import static tech.softhlon.learning.courses.gateway.RestResources.GET_COURSE;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestApiAdapter
@RestController
@RequiredArgsConstructor
class GetCourseController {
    private final GetCourseDetailsService service;
    private final HttpServletRequest httpRequest;

    private static ResponseEntity<CourseDetails> successBody(CourseDetails course) {
        return status(HttpStatus.OK).body(course);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    @GetMapping(GET_COURSE)
    ResponseEntity<?> getCourse(@Validated @RequestBody GetCourseDetailsService.Request request) {
        var result = service.execute(request);
        return switch (result) {
            case Succeeded(CourseDetails courseDetails) -> successBody(courseDetails);
            case CourseNotFoundFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.courses.domain.ListCoursesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static io.softhlon.learning.courses.domain.ListCoursesService.Course;
import static io.softhlon.learning.courses.domain.ListCoursesService.Result.InternalFailure;
import static io.softhlon.learning.courses.domain.ListCoursesService.Result.Success;
import static io.softhlon.learning.courses.gateway.RestResources.LIST_COURSES;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
class ListCoursesController {
    private final ListCoursesService listCoursesService;
    private final HttpServletRequest servletRequest;

    @GetMapping(LIST_COURSES)
    ResponseEntity<?> enrollCourse(@Validated @RequestBody ListCoursesService.Request request) {
        var result = listCoursesService.listCourses(request);
        return switch (result) {
            case Success(List<Course> courses) -> successBody(courses);
            case InternalFailure(Throwable cause) -> internalServerBody(servletRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private static ResponseEntity<List<Course>> successBody(List<Course> courses) {
        return status(HttpStatus.OK).body(courses);
    }
}

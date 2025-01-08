// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import io.softhlon.learning.common.hexagonal.RestApiAdapter;
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
import static io.softhlon.learning.courses.domain.ListCoursesService.Result.Failed;
import static io.softhlon.learning.courses.domain.ListCoursesService.Result.Succeeded;
import static io.softhlon.learning.courses.gateway.RestResources.LIST_COURSES;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@RestApiAdapter
@RestController
@RequiredArgsConstructor
class ListCoursesController {
    private final ListCoursesService service;
    private final HttpServletRequest httpRequest;

    @GetMapping(LIST_COURSES)
    ResponseEntity<?> listCourses(@Validated @RequestBody ListCoursesService.Request request) {
        var result = service.listCourses(request);
        return switch (result) {
            case Succeeded(List<Course> courses) -> successBody(courses);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private static ResponseEntity<List<Course>> successBody(List<Course> courses) {
        return status(HttpStatus.OK).body(courses);
    }
}

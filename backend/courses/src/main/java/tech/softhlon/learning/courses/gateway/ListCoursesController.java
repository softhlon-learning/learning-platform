// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

import lombok.extern.slf4j.Slf4j;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.courses.domain.ListCoursesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.courses.domain.ListCoursesService.CourseView;
import static tech.softhlon.learning.courses.domain.ListCoursesService.Result.Failed;
import static tech.softhlon.learning.courses.domain.ListCoursesService.Result.Succeeded;
import static tech.softhlon.learning.courses.gateway.RestResources.LIST_COURSES;
import static org.springframework.http.ResponseEntity.status;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class ListCoursesController {
    private final ListCoursesService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    private static ResponseEntity<List<CourseView>> successBody(List<CourseView> courses) {
        return status(HttpStatus.OK).body(courses);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    @GetMapping(LIST_COURSES)
    ResponseEntity<?> listCourses() {
        var accountId = authContext.accountId();
        log.info("Requested, accountId: {}", accountId);
        var result = service.execute(authContext.accountId());
        return switch (result) {
            case Succeeded(List<CourseView> courses) -> successBody(courses);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };
    }
}

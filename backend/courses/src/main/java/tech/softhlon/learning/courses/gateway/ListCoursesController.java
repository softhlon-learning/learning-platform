// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.courses.domain.ListCoursesService;
import tech.softhlon.learning.courses.domain.ListCoursesService.CourseView;
import tech.softhlon.learning.courses.domain.ListCoursesService.CoursesView;
import tech.softhlon.learning.courses.domain.ListCoursesService.Result.Failed;
import tech.softhlon.learning.courses.domain.ListCoursesService.Result.Succeeded;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.softhlon.learning.common.text.IdPrinter.printShort;
import static tech.softhlon.learning.courses.gateway.RestResources.LIST_COURSES;

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
    private final SubscriptionCookiesService subscriptionCookiesService;

    /**
     * GET /api/v1/course.
     */
    @GetMapping(LIST_COURSES)
    ResponseEntity<?> listCourses(
          HttpServletResponse response) {

        var accountId = authContext.accountId();
        log.info("controller | List courseList [request]",
              printShort(accountId));

        var result = service.execute(
              authContext.accountId());

        return switch (result) {
            case Succeeded(CoursesView courses) -> successBody(courses, response);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<List<CourseView>> successBody(
          CoursesView courses,
          HttpServletResponse response) {

        subscriptionCookiesService.addASubscriptionCookie(
              response,
              courses.subscribed());

        return status(HttpStatus.OK)
              .body(courses.courseList());

    }

}

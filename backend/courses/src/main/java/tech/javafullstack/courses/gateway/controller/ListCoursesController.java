// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.common.hexagonal.RestApiAdapter;
import tech.javafullstack.common.security.AuthenticationContext;
import tech.javafullstack.courses.domain.ListCoursesService;
import tech.javafullstack.courses.domain.ListCoursesService.CoursesView;
import tech.javafullstack.courses.domain.ListCoursesService.Result.Failed;
import tech.javafullstack.courses.domain.ListCoursesService.Result.Succeeded;

import static org.springframework.http.ResponseEntity.status;
import static tech.javafullstack.common.controller.ResponseBodyHelper.internalServerBody;
import static tech.javafullstack.common.text.IdPrinter.printShort;
import static tech.javafullstack.courses.gateway.controller.RestResources.LIST_COURSES;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * List courses controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class ListCoursesController {

    private final ListCoursesService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;
    private final SubscriptionCookiesService subscriptionCookiesService;
    private final ObjectMapper mapper;

    /**
     * GET /api/v1/course endpoint.
     * @param response HttpServletResponse
     * @return ResponseEntity<?>
     */
    @GetMapping(LIST_COURSES)
    ResponseEntity<?> listCourses(
          HttpServletResponse response) throws JsonProcessingException {

        var accountId = authContext.accountId();
        log.info("controller | request / List courseList",
              printShort(accountId));

        var result = service.execute(authContext.accountId());
        return switch (result) {
            case Succeeded(CoursesView courses) -> successBody(courses, response);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ResponseEntity<?> successBody(
          CoursesView courses,
          HttpServletResponse response) throws JsonProcessingException {

        subscriptionCookiesService.addSubscriptionCookie(
              response,
              String.valueOf(
                    courses
                          .subcriptionType()
                          .name()
                          .toLowerCase()
              )
        );

        var courseList = mapper.writeValueAsString(courses.courseList());
        return status(HttpStatus.OK)
              .body(courseList);

    }

}

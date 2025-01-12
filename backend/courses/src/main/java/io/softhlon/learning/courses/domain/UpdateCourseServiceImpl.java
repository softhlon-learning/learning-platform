// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.softhlon.learning.courses.domain.LoadCourseRepository.Course;
import static io.softhlon.learning.courses.domain.LoadCourseRepository.LoadCourseResult.*;
import static io.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseRequest;
import static io.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersisted;
import static io.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersistenceFailed;
import static io.softhlon.learning.courses.domain.UpdateCourseService.Result.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class UpdateCourseServiceImpl implements UpdateCourseService {
    private final LoadCourseRepository loadCourseRepository;
    private final PersistCourseRepository persistCourseRepository;

    @Override
    public Result execute(Request request) {
        var result = loadCourseRepository.execute(request.courseId());
        return switch (result) {
            case CourseLoaded(Course course) -> updateCourse(request, course);
            case CourseNotFoundInDatabase courseNotFoundInDatabase -> new CourseNotFoundFailed();
            case CourseLoadFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result updateCourse(Request request, Course course) {
        var result = persistCourseRepository.execute(prepareReuqest(request, course));
        return switch (result) {
            case CoursePersisted() -> new Succeeded();
            case CoursePersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private PersistCourseRequest prepareReuqest(
          Request request, Course course) {
        return new PersistCourseRequest(
              request.courseId(),
              course.code(),
              course.orderNo(),
              course.name(),
              course.description(),
              request.content(),
              course.version()
        );
    }
}

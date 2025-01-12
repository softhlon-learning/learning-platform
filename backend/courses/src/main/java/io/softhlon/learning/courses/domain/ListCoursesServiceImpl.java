// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static io.softhlon.learning.courses.domain.ListCoursesService.Result.Failed;
import static io.softhlon.learning.courses.domain.ListCoursesService.Result.Succeeded;
import static io.softhlon.learning.courses.domain.LoadCoursesRepository.Course;
import static io.softhlon.learning.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoadFailed;
import static io.softhlon.learning.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoaded;
import static io.softhlon.learning.courses.domain.LoadEnrollmentRepository.Enrollment;
import static io.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class ListCoursesServiceImpl implements ListCoursesService {
    private final LoadCoursesRepository loadCoursesRepository;
    private final LoadEnrollmentRepository loadEnrollmentRepository;

    @Override
    public Result listCourses(UUID accountId) {
        var result = loadCoursesRepository.execute();
        return switch (result) {
            case CoursesLoadFailed(Throwable cause) -> new Failed(cause);
            case CoursesLoaded(List<Course> courses) -> new Succeeded(toCourseViews(courses, accountId));
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private List<CourseView> toCourseViews(List<Course> courses, UUID accountId) {
        return courses.stream()
              .map(course -> courseView(course, accountId))
              .map(courseView -> mapEnrolled(courseView, accountId))
              .sorted(Comparator.comparing(CourseView::orderNo))
              .toList();
    }

    private CourseView courseView(Course course, UUID accountId) {
        return new CourseView(
              course.courseId(),
              course.code(),
              course.orderNo(),
              course.name(),
              course.description(),
              course.content(),
              false);
    }

    private CourseView mapEnrolled(CourseView courseView, UUID accountId) {
        var result = loadEnrollmentRepository.execute(accountId, courseView.id());
        return switch (result) {
            case EnrollmentLoaded(Enrollment enrollment) -> updateEnrollmentFlag(courseView, enrollment);
            case EnrollmentLoadFailed(_), EnrollmentNotFoundInDatabase() -> courseView;
        };
    }

    private CourseView updateEnrollmentFlag(CourseView courseView, Enrollment enrollment) {
        return new CourseView(
              courseView.id(),
              courseView.code(),
              courseView.orderNo(),
              courseView.name(),
              courseView.description(),
              enrollment.content(),
              true
        );
    }
}

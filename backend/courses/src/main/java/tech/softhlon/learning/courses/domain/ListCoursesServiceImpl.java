// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.ListCoursesService.Result.Failed;
import tech.softhlon.learning.courses.domain.ListCoursesService.Result.Succeeded;
import tech.softhlon.learning.courses.domain.LoadCoursesRepository.Course;
import tech.softhlon.learning.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoadFailed;
import tech.softhlon.learning.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoaded;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.Enrollment;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoadFailed;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoaded;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentNotFoundInDatabase;
import tech.softhlon.learning.subscriptions.gateway.CheckSubscriptionOperator;
import tech.softhlon.learning.subscriptions.gateway.CheckSubscriptionOperator.CheckSusbcriptionRequest;
import tech.softhlon.learning.subscriptions.gateway.CheckSubscriptionOperator.CheckSusbcriptionResult.Subscribed;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class ListCoursesServiceImpl implements ListCoursesService {

    private final LoadCoursesRepository loadCoursesRepository;
    private final LoadEnrollmentRepository loadEnrollmentRepository;
    private final CheckSubscriptionOperator checkSubscriptionOperator;
    private final HideCourseContentService hideCourseContentService;

    @Override
    public Result execute(
          UUID accountId) {

        var result = loadCoursesRepository.execute();

        return switch (result) {
            case CoursesLoadFailed(Throwable cause) -> new Failed(cause);
            case CoursesLoaded(List<Course> courses) -> new Succeeded(toCourseViews(courses, accountId));
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CoursesView toCourseViews(
          List<Course> courses,
          UUID accountId) {

        var result = checkSubscriptionOperator.execute(
              new CheckSusbcriptionRequest(accountId));

        boolean subscribed = false;
        if (result instanceof Subscribed) {
            subscribed = true;
        }

        return new CoursesView(
              courses.stream()
                    .map(course -> courseView(course, accountId))
                    .map(courseView -> mapEnrolled(courseView, accountId))
                    .map(courseView -> hideLockedLectures(result instanceof Subscribed, courseView))
                    .sorted(Comparator.comparing(CourseView::orderNo))
                    .toList(),
              subscribed);

    }

    private CourseView courseView(
          Course course,
          UUID accountId) {

        return new CourseView(
              course.courseId(),
              course.code(),
              course.orderNo(),
              course.name(),
              course.description(),
              course.content(),
              false);

    }

    private CourseView hideLockedLectures(
          boolean subscribed,
          CourseView courseView) {

        if (subscribed) {
            return courseView;
        } else {
            String contentJson = courseView.content();
            String updatedContentJson = hideCourseContentService.execute(contentJson);
            return new CourseView(
                  courseView.id(),
                  courseView.code(),
                  courseView.orderNo(),
                  courseView.name(),
                  courseView.description(),
                  updatedContentJson,
                  courseView.enrolled()
            );
        }

    }

    private CourseView mapEnrolled(
          CourseView courseView,
          UUID accountId) {

        var result = loadEnrollmentRepository.execute(
              accountId,
              courseView.id());

        return switch (result) {
            case EnrollmentLoaded(Enrollment enrollment) -> updateEnrollmentFlag(courseView, enrollment);
            case EnrollmentLoadFailed(_), EnrollmentNotFoundInDatabase() -> courseView;
        };

    }

    private CourseView updateEnrollmentFlag(
          CourseView courseView,
          Enrollment enrollment) {

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

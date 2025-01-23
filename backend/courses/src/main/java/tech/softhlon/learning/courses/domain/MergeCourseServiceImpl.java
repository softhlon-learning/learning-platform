// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.CourseContent;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.JsonToCourseContentRequest;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.JsonToCourseContentResult.JsonConvertFailed;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.JsonToCourseContentResult.JsonConverted;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.Enrollment;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsRequest;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentLoadFailed;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentsLoaded;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMergeFailed;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMerged;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class MergeCourseServiceImpl implements MergeCourseService {
    private final JsonToCourseContentService jsonToCourseContentService;
    private final LoadEnrollmentsRepository loadEnrollmentsRepository;

    @Override
    public MergeCourseResult execute(MergeCourseReuqest reuqest) {
        var result = jsonToCourseContentService.execute(
              new JsonToCourseContentRequest(reuqest.content()));

        return switch (result) {
            case JsonConverted(CourseContent content) -> processCourseContent(reuqest, content);
            case JsonConvertFailed(Throwable cause) -> new CourseMergeFailed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private MergeCourseResult processCourseContent(
          MergeCourseReuqest reuqest,
          CourseContent content) {

        var result = loadEnrollmentsRepository.execute(
              new ListEnrollmentsRequest(reuqest.courseId()));

        return switch (result) {
            case EnrollmentLoadFailed(Throwable cause) -> new CourseMergeFailed(cause);
            case EnrollmentsLoaded(List<Enrollment> enrollments) -> processEnrollments(content, enrollments);
        };
    }

    private MergeCourseResult processEnrollments(
          CourseContent content,
          List<Enrollment> enrollments) {

        for (Enrollment enrollment : enrollments) {
            var result = jsonToCourseContentService.execute(
                  new JsonToCourseContentRequest(
                        enrollment.content()));

            if (result instanceof JsonConverted(CourseContent enrollmentContent)) {
                updateContent(content, enrollmentContent);
            }
        }

        return new CourseMerged();
    }

    private void updateContent(
          CourseContent content,
          CourseContent enrollmentContent) {
    }
}


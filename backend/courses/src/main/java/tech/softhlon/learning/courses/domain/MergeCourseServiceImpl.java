// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.JsonCourseContentService.CourseContent;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.Enrollment;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsRequest;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentLoadFailed;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentsLoaded;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMergeFailed;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMerged;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentRequest;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersisted;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersistenceFailed;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class MergeCourseServiceImpl implements MergeCourseService {
    private final JsonCourseContentService jsonCourseContentService;
    private final LoadEnrollmentsRepository loadEnrollmentsRepository;
    private final PersistEnrollmentRepository persistEnrollmentRepository;

    @Override
    public MergeCourseResult execute(MergeCourseReuqest reuqest) {
        try {
            var content = jsonCourseContentService.jsonToCurseContent(reuqest.content());
            return processCourseContent(reuqest, content);
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CourseMergeFailed(cause);
        }
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
            case EnrollmentsLoaded(List<Enrollment> enrollments) -> processEnrollments(reuqest, content, enrollments);
        };
    }

    private MergeCourseResult processEnrollments(
          MergeCourseReuqest reuqest,
          CourseContent content,
          List<Enrollment> enrollments) {

        for (Enrollment enrollment : enrollments) {
            var enrollmentContent = jsonCourseContentService.jsonToCurseContent(reuqest.content());
            updateContent(content, enrollmentContent);
            persistEnrollment(reuqest, enrollment, enrollmentContent);

        }
        return new CourseMerged();
    }

    private void updateContent(
          CourseContent content,
          CourseContent enrollmentContent) {
    }

    MergeCourseResult persistEnrollment(
          MergeCourseReuqest reuqest,
          Enrollment enrollment,
          CourseContent enrollmentContent) {

        var result = persistEnrollmentRepository.execute(
              new PersistEnrollmentRequest(
                    reuqest.courseId(),
                    enrollment.accountId(),
                    reuqest.content(),
                    enrollment.enrolledTime(),
                    enrollment.completedTime()));

        return switch (result) {
            case EnrollmentPersisted enrollmentPersisted -> new CourseMerged();
            case EnrollmentPersistenceFailed(Throwable cause) -> new CourseMergeFailed(cause);
        };
    }
}

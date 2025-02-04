// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.CheckCourseRepository.CheckCourseResult.CheckCourseFailed;
import tech.softhlon.learning.courses.domain.CheckCourseRepository.CheckCourseResult.CourseExists;
import tech.softhlon.learning.courses.domain.CheckCourseRepository.CheckCourseResult.CourseNotFound;
import tech.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollementPersistenceFailed;
import tech.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollmentPersisted;
import tech.softhlon.learning.courses.domain.EnrollCourseService.Result.CourseNotFoundFailed;
import tech.softhlon.learning.courses.domain.EnrollCourseService.Result.Failed;
import tech.softhlon.learning.courses.domain.EnrollCourseService.Result.Succeeded;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Enroll course service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class EnrollCourseServiceImpl implements EnrollCourseService {

    private static final String COURSE_NOT_FOUND = "Course not found";
    private final CheckCourseRepository checkCourseRepository;
    private final CreateEnrollmentRepository createEnrollmentRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          UUID accountId,
          UUID courseId) {

        var courseExists = checkCourseRepository
              .execute(courseId);

        return switch (courseExists) {
            case CourseExists() -> persistEnrollment(accountId, courseId);
            case CourseNotFound() -> new CourseNotFoundFailed(COURSE_NOT_FOUND);
            case CheckCourseFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistEnrollment(
          UUID accountId,
          UUID courseId) {

        var result = createEnrollmentRepository.execute(
              courseId,
              accountId,
              OffsetDateTime.now()
        );

        return switch (result) {
            case EnrollmentPersisted(UUID id) -> new Succeeded();
            case EnrollementPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}

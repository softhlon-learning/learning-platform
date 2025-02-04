// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.CheckEnrollmentFailed;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.EnrollmentExists;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.EnrollmentNotFound;
import tech.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollementDeletionFailed;
import tech.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollmentDeleted;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.EnrollmentNotFoundFailed;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.Failed;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.Succeeded;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Unenroll course service implementation..
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
class UnenrollCourseServiceImpl implements UnenrollCourseService {

    private static final String ENROLLMENT_NOT_FOUND = "Enrollment not found";
    private final CheckEnrollmentRepository checkEnrollmentRepository;
    private final DeleteEnrollmentRepository deleteEnrollmentRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          UUID accountId,
          UUID courseId) {

        var enrollmentExists = checkEnrollmentRepository
              .execute(
                    accountId,
                    courseId
              );

        return switch (enrollmentExists) {
            case EnrollmentExists() -> deleteEnrollment(accountId, courseId);
            case EnrollmentNotFound() -> new EnrollmentNotFoundFailed(ENROLLMENT_NOT_FOUND);
            case CheckEnrollmentFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result deleteEnrollment(
          UUID accountId,
          UUID courseId) {

        var result = deleteEnrollmentRepository
              .execute(courseId, accountId);

        return switch (result) {
            case EnrollmentDeleted() -> new Succeeded();
            case EnrollementDeletionFailed(Throwable cause) -> new Failed(cause);
        };

    }

}

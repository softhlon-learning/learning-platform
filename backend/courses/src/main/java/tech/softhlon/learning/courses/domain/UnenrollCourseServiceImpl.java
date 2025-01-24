// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentRequest;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.CheckEnrollmentFailed;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.EnrollmentExists;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.EnrollmentNotFound;
import tech.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentRequest;
import tech.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollementDeletionFailed;
import tech.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollmentDeleted;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.EnrollmentNotFoundFailed;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.Failed;
import tech.softhlon.learning.courses.domain.UnenrollCourseService.Result.Succeeded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
class UnenrollCourseServiceImpl implements UnenrollCourseService {

    private static final String ENROLLMENT_NOT_FOUND = "Enrollment not found";
    private final CheckEnrollmentRepository checkEnrollmentRepository;
    private final DeleteEnrollmentRepository deleteEnrollmentRepository;

    @Override
    public Result execute(
          Request request) {

        var enrollmentExists = checkEnrollmentRepository.execute(
              new CheckEnrollmentRequest(
                    request.accountId(),
                    request.courseId()));

        return switch (enrollmentExists) {
            case EnrollmentExists() -> deleteEnrollment(request);
            case EnrollmentNotFound() -> new EnrollmentNotFoundFailed(ENROLLMENT_NOT_FOUND);
            case CheckEnrollmentFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result deleteEnrollment(
          Request request) {

        var result = deleteEnrollmentRepository.execute(
              prepareRequest(request));

        return switch (result) {
            case EnrollmentDeleted() -> new Succeeded();
            case EnrollementDeletionFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private DeleteEnrollmentRequest prepareRequest(
          Request request) {

        return new DeleteEnrollmentRequest(
              request.courseId(),
              request.accountId()
        );

    }

}

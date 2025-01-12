// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentRequest;
import static io.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.*;
import static io.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentRequest;
import static io.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollementDeletionFailed;
import static io.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollmentDeleted;
import static io.softhlon.learning.courses.domain.UnenrollCourseService.Result.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
class UnenrollCourseServiceImpl implements UnenrollCourseService {
    private final CheckEnrollmentRepository checkEnrollmentRepository;
    private final DeleteEnrollmentRepository deleteEnrollmentRepository;

    @Override
    public Result execute(Request request) {
        var enrollmentExists = checkEnrollmentRepository.execute(
              new CheckEnrollmentRequest(
                    request.accountId(),
                    request.courseId()));

        return switch (enrollmentExists) {
            case EnrollmentExists() -> deleteEnrollment(request);
            case EnrollmentNotFound() -> new EnrollmentNotFoundFailed("Enrollment not found");
            case CheckEnrollmentFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result deleteEnrollment(Request request) {
        var result = deleteEnrollmentRepository.execute(prepareRequest(request));
        return switch (result) {
            case EnrollmentDeleted() -> new Succeeded();
            case EnrollementDeletionFailed(Throwable cause) -> new Result.Failed(cause);
        };
    }

    private DeleteEnrollmentRequest prepareRequest(Request request) {
        return new DeleteEnrollmentRequest(
              request.courseId(),
              request.accountId()
        );
    }
}

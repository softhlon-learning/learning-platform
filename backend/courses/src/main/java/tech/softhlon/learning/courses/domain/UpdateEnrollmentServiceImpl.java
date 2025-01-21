// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.Enrollment;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoadFailed;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoaded;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentNotFoundInDatabase;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentRequest;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersisted;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersistenceFailed;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService.Result.EnrollmentNotFoundFailed;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService.Result.Failed;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentService.Result.Succeeded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class UpdateEnrollmentServiceImpl implements UpdateEnrollmentService {
    private static final String ENROLLMENT_NOT_FOUND = "Enrollment not found";
    private final LoadEnrollmentRepository loadEnrollmentRepository;
    private final PersistEnrollmentRepository persistEnrollmentRepository;

    @Override
    public Result execute(Request request) {
        var result = loadEnrollmentRepository.execute(
              request.accountId(),
              request.courseId());
        return switch (result) {
            case EnrollmentLoaded(Enrollment enrollment) -> updateEnrollment(request, enrollment);
            case EnrollmentNotFoundInDatabase() -> new EnrollmentNotFoundFailed(ENROLLMENT_NOT_FOUND);
            case EnrollmentLoadFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result updateEnrollment(Request request, Enrollment enrollment) {
        var result = persistEnrollmentRepository.execute(prepareReuqest(request, enrollment));
        return switch (result) {
            case EnrollmentPersisted() -> new Succeeded();
            case EnrollmentPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private PersistEnrollmentRequest prepareReuqest(
          Request request, Enrollment course) {
        return new PersistEnrollmentRequest(
              request.courseId(),
              request.accountId(),
              request.content(),
              course.enrolledTime(),
              course.completedTime()
        );
    }
}

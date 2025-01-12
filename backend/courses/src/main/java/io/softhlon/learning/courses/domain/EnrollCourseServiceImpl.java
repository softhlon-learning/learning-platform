// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.courses.domain.CheckCourseRepository.CheckCourseRequest;
import io.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

import static io.softhlon.learning.courses.domain.CheckCourseRepository.CheckCourseResult.*;
import static io.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollementPersistenceFailed;
import static io.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollmentPersisted;
import static io.softhlon.learning.courses.domain.EnrollCourseService.Result.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class EnrollCourseServiceImpl implements EnrollCourseService {
    private final CheckCourseRepository checkCourseRepository;
    private final CreateEnrollmentRepository createEnrollmentRepository;

    @Override
    public Result execute(Request request) {
        var courseExists = checkCourseRepository.execute(new CheckCourseRequest(request.courseId()));
        return switch (courseExists) {
            case CourseExists() -> persistEnrollment(request);
            case CourseNotFound() -> new CourseNotFoundFailed("Course not found");
            case CheckCourseFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistEnrollment(Request request) {
        var result = createEnrollmentRepository.execute(prepareRequest(request));
        return switch (result) {
            case EnrollmentPersisted(UUID id) -> new Succeeded();
            case EnrollementPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private CreateEnrollmentRequest prepareRequest(Request request) {
        return new CreateEnrollmentRequest(
              request.courseId(),
              request.accountId(),
              EnrollmentStatus.ACTIVE.name(),
              OffsetDateTime.now()
        );
    }
}

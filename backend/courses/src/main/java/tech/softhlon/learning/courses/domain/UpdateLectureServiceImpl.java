// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.Enrollment;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoadFailed;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoaded;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentNotFoundInDatabase;
import tech.softhlon.learning.courses.domain.UpdateLectureService.Result.Failed;
import tech.softhlon.learning.courses.domain.UpdateLectureService.Result.LectureNotFoundFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class UpdateLectureServiceImpl implements UpdateLectureService {

    private final LoadEnrollmentRepository loadEnrollmentRepository;
    private final ContentService contentService;

    @Override
    public Result execute(
          Request request) {

        var result = loadEnrollmentRepository.execute(
              request.accountId(),
              request.courseId());

        return switch (result) {
            case EnrollmentLoaded(Enrollment enrollment) -> processLectureUpdate(request, enrollment);
            case EnrollmentNotFoundInDatabase() -> new LectureNotFoundFailed();
            case EnrollmentLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result processLectureUpdate(
          Request request,
          Enrollment enrollment) {

        var enrollmentContent = contentService.jsonToCurseContent(enrollment.content());
        return null;

    }

}

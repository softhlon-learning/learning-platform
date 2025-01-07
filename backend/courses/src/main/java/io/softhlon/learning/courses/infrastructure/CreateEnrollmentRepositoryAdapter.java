// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.CreateEnrollmentRepository;
import io.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollementPersistenceFailed;
import io.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollmentPersisted;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CreateEnrollmentRepositoryAdapter implements CreateEnrollmentRepository {
    private final EnrollmentsJpaRepository enrollmentsRepo;
    private final CoursesJpaRepository coursesRepo;

    @Override
    public CreateEnrollmentResult execute(CreateEnrollmentRequest request) {
        try {
            var course = coursesRepo.findById(request.courseId()).get();
            var createdEnrollment = enrollmentsRepo.save(toEnrollment(request, course));
            return new EnrollmentPersisted(createdEnrollment.getId());
        } catch (Throwable cause) {
            return new EnrollementPersistenceFailed(cause);
        }
    }

    private EnrollmentEntity toEnrollment(
          CreateEnrollmentRequest request,
          CourseEntity course) {
        return EnrollmentEntity.builder()
              .course(course)
              .status(request.status())
              .content(request.content())
              .enrolledTime(request.enrolledTime())
              .unenrolledTime(request.unenrolledTime())
              .completedTime(request.completedTime())
              .build();
    }
}

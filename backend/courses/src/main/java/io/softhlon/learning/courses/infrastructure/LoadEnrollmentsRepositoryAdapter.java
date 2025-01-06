// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.LoadEnrollmentRepository;
import io.softhlon.learning.courses.domain.LoadEnrollmentsRepository;
import io.softhlon.learning.courses.domain.LoadEnrollmentsRepository.Result.InternalFailure;
import io.softhlon.learning.courses.domain.LoadEnrollmentsRepository.Result.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadEnrollmentsRepositoryAdapter implements LoadEnrollmentsRepository {
    private final EnrollmentsJpaRepository enrollmentsRepo;

    @Override
    public Result execute() {
        try {
            var stream = StreamSupport.stream(enrollmentsRepo.findAll().spliterator(), false);
            var enrollments = stream.map(this::toEnrollment).toList();
            return new Success(enrollments);
        } catch (Throwable cause) {
            return new InternalFailure(cause);
        }
    }

    private Enrollment toEnrollment(EnrollmentEntity entity) {
        CourseEntity course = entity.getCourse();
        return new Enrollment(
              entity.getId(),
              new LoadEnrollmentRepository.Course(course.getName(), course.getDescription()),
              entity.getStatus(),
              entity.getEnrolledTime(),
              entity.getUnenrolledTime(),
              entity.getCompletedTime()
        );
    }
}

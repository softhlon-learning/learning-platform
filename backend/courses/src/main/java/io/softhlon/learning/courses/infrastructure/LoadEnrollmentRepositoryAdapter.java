// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.LoadEnrollmentRepository;
import io.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoadFailed;
import io.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoaded;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.log.SubSystemLogging;
import org.springframework.stereotype.Service;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadEnrollmentRepositoryAdapter implements LoadEnrollmentRepository {
    private final EnrollmentsJpaRepository enrollmentsRepo;

    @Override
    public LoadEnrollmentResult execute(UUID id) {
        try {
            var entity = enrollmentsRepo.findById(id).get();
            return new EnrollmentLoaded(toEnrollment(entity));
        } catch (Throwable cause) {
            log.error("", cause);
            return new EnrollmentLoadFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Enrollment toEnrollment(EnrollmentEntity entity) {
        CourseEntity course = entity.getCourse();
        return new Enrollment(
              entity.getId(),
              new Course(course.getName(), course.getDescription()),
              entity.getStatus(),
              entity.getEnrolledTime(),
              entity.getUnenrolledTime(),
              entity.getCompletedTime()
        );
    }
}

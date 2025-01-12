// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.LoadEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.*;

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
    public LoadEnrollmentResult execute(UUID accountId, UUID courseId) {
        try {
            var entity = enrollmentsRepo.findByAccountIdAndCourseId(accountId, courseId);
            if (entity.isPresent()) {
                return new EnrollmentLoaded(toEnrollment(entity.get()));
            } else {
                return new EnrollmentNotFoundInDatabase();
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollmentLoadFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Enrollment toEnrollment(EnrollmentEntity entity) {
        return new Enrollment(
              entity.getId(),
              entity.getAccountId(),
              entity.getStatus(),
              entity.getContent(),
              entity.getEnrolledTime(),
              entity.getCompletedTime()
        );
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------
package tech.javafullstack.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;
import tech.javafullstack.courses.domain.LoadEnrollmentsRepository;
import tech.javafullstack.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentLoadFailed;
import tech.javafullstack.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentsLoaded;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load enrollments repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadEnrollmentsRepositoryAdapter implements LoadEnrollmentsRepository {

    private final EnrollmentsJpaRepository enrollmentsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public ListEnrollmentsResult execute(
          UUID courseId) {

        try {
            var entities = enrollmentsJpaRepository.findByCourseId(courseId);
            return new EnrollmentsLoaded(
                  entities.stream()
                        .map(this::toEnrollment)
                        .toList()
            );
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollmentLoadFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Enrollment toEnrollment(
          EnrollmentEntity entity) {

        return new Enrollment(
              entity.getId(),
              entity.getAccountId(),
              entity.getContent(),
              entity.getEnrolledAt(),
              entity.getCompletedAt()
        );

    }

}

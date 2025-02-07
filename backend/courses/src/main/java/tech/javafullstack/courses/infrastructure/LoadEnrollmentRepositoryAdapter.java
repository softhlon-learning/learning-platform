// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;
import tech.javafullstack.courses.domain.LoadEnrollmentRepository;
import tech.javafullstack.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoadFailed;
import tech.javafullstack.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoaded;
import tech.javafullstack.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentNotFoundInDatabase;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load enrollment repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadEnrollmentRepositoryAdapter implements LoadEnrollmentRepository {

    private final EnrollmentsJpaRepository enrollmentsRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadEnrollmentResult execute(
          UUID accountId,
          UUID courseId) {

        try {
            var entity = enrollmentsRepo.findByAccountIdAndCourseId(
                  accountId,
                  courseId
            );

            if (entity.isPresent()) {
                return new EnrollmentLoaded(
                      toEnrollment(entity.get()));
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

    private Enrollment toEnrollment(
          EnrollmentEntity entity) {

        return new Enrollment(
              entity.getId(),
              entity.getCourse().getId(),
              entity.getAccountId(),
              entity.getContent(),
              entity.getEnrolledAt(),
              entity.getCompletedAt()
        );

    }
}

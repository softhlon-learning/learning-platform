// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;
import tech.javafullstack.courses.domain.DeleteEnrollmentRepository;
import tech.javafullstack.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollementDeletionFailed;
import tech.javafullstack.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollmentDeleted;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Delete enrollment repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class DeleteEnrollmentRepositoryAdapter implements DeleteEnrollmentRepository {

    private final EnrollmentsJpaRepository enrollmentsRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteEnrollmentResult execute(
          UUID courseId,
          UUID accountId) {

        try {
            enrollmentsRepo.deleteByAccountIdAndCourseId(
                  accountId,
                  courseId
            );
            return new EnrollmentDeleted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollementDeletionFailed(cause);
        }

    }

}

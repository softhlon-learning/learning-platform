// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;
import tech.javafullstack.courses.domain.PersistEnrollmentRepository;
import tech.javafullstack.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentNotPresentFoundFailed;
import tech.javafullstack.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersisted;
import tech.javafullstack.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist enrollment repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class PersistEnrollmentRepositoryAdapter implements PersistEnrollmentRepository {

    private final EnrollmentsJpaRepository enrollmentsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistEnrollmentResult execute(
          PersistEnrollmentRequest request) {

        try {
            var entityOpt = enrollmentsJpaRepository.findByAccountIdAndCourseId(
                  request.accountId(),
                  request.courseId()
            );
            if (entityOpt.isPresent()) {
                var entity = entityOpt.get();
                updateEntity(request, entity);
                enrollmentsJpaRepository.save(entity);
                return new EnrollmentPersisted();
            } else {
                return new EnrollmentNotPresentFoundFailed();
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollmentPersistenceFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void updateEntity(
          PersistEnrollmentRequest request,
          EnrollmentEntity entity) {

        entity.setAccountId(request.accountId());
        entity.setContent(request.content());
        entity.setEnrolledAt(request.enrolledTime());
        entity.setCompletedAt(request.completedTime());

    }

}

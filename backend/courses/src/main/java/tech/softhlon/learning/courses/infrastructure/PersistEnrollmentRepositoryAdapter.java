// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository;

import static tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersisted;
import static tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class PersistEnrollmentRepositoryAdapter implements PersistEnrollmentRepository {
    private final EnrollmentsJpaRepository enrollmentsJpaRepository;

    @Override
    public PersistEnrollmentResult execute(PersistEnrollmentRequest request) {
        try {
            var entityOpt = enrollmentsJpaRepository.findByAccountIdAndCourseId(
                  request.accountId(),
                  request.courseId());
            if (entityOpt.isPresent()) {
                var entity = entityOpt.get();
                updateEntity(request, entity);
                enrollmentsJpaRepository.save(entity);
            }
            return new EnrollmentPersisted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollmentPersistenceFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void updateEntity(PersistEnrollmentRequest request, EnrollmentEntity entity) {
        entity.setAccountId(request.accountId());
        entity.setStatus(request.status());
        entity.setContent(request.content());
        entity.setEnrolledTime(request.enrolledTime());
        entity.setCompletedTime(request.completedTime());
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.UpdateEnrollmentRepository;
import io.softhlon.learning.courses.domain.UpdateEnrollmentRepository.UpdateEnrollmentResult.EnrollmentUpdateFailed;
import io.softhlon.learning.courses.domain.UpdateEnrollmentRepository.UpdateEnrollmentResult.EnrollmentUpdated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class UpdateEnrollmentRepositoryAdapter implements UpdateEnrollmentRepository {
    private final EnrollmentsJpaRepository enrollmentsRepo;

    @Override
    public UpdateEnrollmentResult execute(Enrollment enrollment) {
        try {
            var entity = enrollmentsRepo.findById(enrollment.id()).get();
            updateEntity(enrollment, entity);
            enrollmentsRepo.save(entity);
            return new EnrollmentUpdated(enrollment.id());
        } catch (Throwable cause) {
            log.error("", cause);
            return new EnrollmentUpdateFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void updateEntity(Enrollment enrollment, EnrollmentEntity entity) {
        entity.setStatus(enrollment.status());
        entity.setContent(enrollment.content());
        entity.setEnrolledTime(enrollment.enrolledTime());
        entity.setUnenrolledTime(enrollment.unenrolledTime());
    }
}

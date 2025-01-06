// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.UpdateEnrollmentRepository;
import io.softhlon.learning.courses.domain.UpdateEnrollmentRepository.Result.InternalFailure;
import io.softhlon.learning.courses.domain.UpdateEnrollmentRepository.Result.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class UpdateEnrollmentRepositoryAdapter implements UpdateEnrollmentRepository {
    private final EnrollmentsJpaRepository enrollmentsRepo;

    @Override
    public Result execute(Enrollment enrollment) {
        try {
            var entity = enrollmentsRepo.findById(enrollment.id()).get();
            updateEntity(enrollment, entity);
            enrollmentsRepo.save(entity);
            return new Success(enrollment.id());
        } catch (Throwable cause) {
            return new InternalFailure(cause);
        }
    }

    private void updateEntity(Enrollment enrollment, EnrollmentEntity entity) {
        entity.setStatus(enrollment.status());
        entity.setContent(enrollment.content());
        entity.setEnrolledTime(enrollment.enrolledTime());
        entity.setUnenrolledTime(enrollment.unenrolledTime());
    }
}

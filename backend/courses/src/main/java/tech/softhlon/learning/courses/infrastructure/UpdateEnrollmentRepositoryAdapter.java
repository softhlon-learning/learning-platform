// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentRepository;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentRepository.UpdateEnrollmentResult.EnrollmentUpdateFailed;
import tech.softhlon.learning.courses.domain.UpdateEnrollmentRepository.UpdateEnrollmentResult.EnrollmentUpdated;

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
    public UpdateEnrollmentResult execute(
          Enrollment enrollment) {

        try {
            var entity = enrollmentsRepo.
                  findById(enrollment.id()).get();

            updateEntity(
                  enrollment,
                  entity);

            enrollmentsRepo.save(
                  entity);

            return new EnrollmentUpdated(enrollment.id());

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new EnrollmentUpdateFailed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void updateEntity(
          Enrollment enrollment,
          EnrollmentEntity entity) {

        entity.setContent(
              enrollment.content());

    }

}

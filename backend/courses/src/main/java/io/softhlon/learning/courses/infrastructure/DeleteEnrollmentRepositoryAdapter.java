// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.DeleteEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollementDeletionFailed;
import static io.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollmentDeleted;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class DeleteEnrollmentRepositoryAdapter implements DeleteEnrollmentRepository {
    private final EnrollmentsJpaRepository enrollmentsRepo;

    @Override
    public DeleteEnrollmentResult execute(DeleteEnrollmentRequest request) {
        try {
            var entity = enrollmentsRepo.findByAccountIdAndCourseId(
                  request.accountId(),
                  request.courseId())
                  .get();
            entity.getCourse().setEnrollment(null);
            enrollmentsRepo.save(entity);
            return new EnrollmentDeleted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollementDeletionFailed(cause);
        }
    }
}

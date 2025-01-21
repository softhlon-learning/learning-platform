// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.DeleteEnrollmentRepository;
import tech.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollementDeletionFailed;
import tech.softhlon.learning.courses.domain.DeleteEnrollmentRepository.DeleteEnrollmentResult.EnrollmentDeleted;

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
            enrollmentsRepo.deleteByAccountIdAndCourseId(
                  request.accountId(),
                  request.courseId());
            return new EnrollmentDeleted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollementDeletionFailed(cause);
        }
    }
}

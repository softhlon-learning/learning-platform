// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.CheckEnrollmentFailed;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.EnrollmentExists;
import tech.softhlon.learning.courses.domain.CheckEnrollmentRepository.CheckEnrollmentResult.EnrollmentNotFound;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckEnrollmentRepositoryAdapter implements CheckEnrollmentRepository {

    private final EnrollmentsJpaRepository enrollmentsRepo;

    @Override
    public CheckEnrollmentResult execute(
          UUID accountId,
          UUID courseId) {

        try {
            return enrollmentsRepo
                  .existsByAccountIdAndCourseId(accountId, courseId)
                  ? new EnrollmentExists()
                  : new EnrollmentNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckEnrollmentFailed(cause);
        }

    }

}

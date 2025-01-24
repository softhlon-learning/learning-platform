// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------
package tech.softhlon.learning.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentLoadFailed;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentsLoaded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadEnrollmentsRepositoryAdapter implements LoadEnrollmentsRepository {

    private final EnrollmentsJpaRepository enrollmentsJpaRepository;

    @Override
    public ListEnrollmentsResult execute(
          ListEnrollmentsRequest request) {

        try {
            var entities = enrollmentsJpaRepository.
                  findByCourseId(
                        request.courseId());

            return new EnrollmentsLoaded(
                  entities.stream()
                        .map(this::toEnrollment)
                        .toList());

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
              entity.getAccountId(),
              entity.getContent(),
              entity.getEnrolledTime(),
              entity.getCompletedTime()
        );

    }
}

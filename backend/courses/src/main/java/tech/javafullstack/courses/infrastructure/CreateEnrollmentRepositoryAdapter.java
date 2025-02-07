// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;
import tech.javafullstack.courses.domain.CreateEnrollmentRepository;
import tech.javafullstack.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollementPersistenceFailed;
import tech.javafullstack.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollmentPersisted;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create enrollment repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CreateEnrollmentRepositoryAdapter implements CreateEnrollmentRepository {

    private final EnrollmentsJpaRepository enrollmentsRepo;
    private final CoursesJpaRepository coursesRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateEnrollmentResult execute(
          UUID courseId,
          UUID accountId,
          OffsetDateTime enrolledAt) {

        try {
            var course = coursesRepo
                  .findById(courseId)
                  .get();

            var createdEnrollment = enrollmentsRepo.save(
                  toEnrollment(
                        accountId,
                        enrolledAt,
                        course)
            );

            return new EnrollmentPersisted(createdEnrollment.getId());

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollementPersistenceFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private EnrollmentEntity toEnrollment(
          UUID accountId,
          OffsetDateTime enrolledTime,
          CourseEntity course) {

        return EnrollmentEntity.builder()
              .accountId(accountId)
              .course(course)
              .content(course.getContent())
              .enrolledAt(enrolledTime)
              .build();

    }

}

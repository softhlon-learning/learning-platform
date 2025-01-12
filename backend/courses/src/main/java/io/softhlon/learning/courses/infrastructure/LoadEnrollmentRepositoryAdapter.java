// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.LoadEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadEnrollmentRepositoryAdapter implements LoadEnrollmentRepository {
    private final CoursesJpaRepository coursesRepo;

    @Override
    public LoadEnrollmentResult execute(UUID id) {
        try {
            var entity = coursesRepo.findById(id);
            if (entity.isPresent()) {
                return new EnrollmentLoaded(toCourse(entity.get()));
            } else {
                return new EnrollmentNotFoundInDatabase();
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollmentLoadFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Course toCourse(CourseEntity entity) {
        return new Course(
              entity.getId(),
              entity.getCode(),
              entity.getOrderNo(),
              entity.getName(),
              entity.getDescription(),
              entity.getContent(),
              entity.getVersion(),
              entity.getEnrollment() != null ? true : false
        );
    }
}

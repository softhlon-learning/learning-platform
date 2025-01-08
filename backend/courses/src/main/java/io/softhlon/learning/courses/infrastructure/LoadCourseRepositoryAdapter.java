// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.LoadCourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.softhlon.learning.courses.domain.LoadCourseRepository.LoadCourseResult.CourseLoadFailed;
import static io.softhlon.learning.courses.domain.LoadCourseRepository.LoadCourseResult.CourseLoaded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadCourseRepositoryAdapter implements LoadCourseRepository {
    private final CoursesJpaRepository coursesRepo;

    @Override
    public LoadCourseResult execute(UUID id) {
        try {
            var entity = coursesRepo.findById(id).get();
            return new CourseLoaded(toCourse(entity));
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CourseLoadFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Course toCourse(CourseEntity entity) {
        return new Course(
              entity.getId(),
              entity.getName(),
              entity.getDescription(),
              entity.getContent(),
              null
        );
    }
}

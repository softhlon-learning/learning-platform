// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;
import tech.javafullstack.courses.domain.LoadCoursesRepository;
import tech.javafullstack.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoadFailed;
import tech.javafullstack.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoaded;

import java.util.stream.StreamSupport;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load courses repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadCoursesRepositoryAdapter implements LoadCoursesRepository {

    private final CoursesJpaRepository coursesRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadCoursesResult execute() {

        try {
            var stream = StreamSupport.stream(
                  coursesRepo.findAll().spliterator(),
                  false);

            var courses = stream.
                  map(this::toCourse)
                  .toList();

            return new CoursesLoaded(courses);
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CoursesLoadFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Course toCourse(
          CourseEntity entity) {

        return new Course(
              entity.getId(),
              entity.getCode(),
              entity.getOrderNo(),
              entity.getName(),
              entity.getDescription(),
              entity.getContent(),
              entity.getVersion()
        );

    }

}

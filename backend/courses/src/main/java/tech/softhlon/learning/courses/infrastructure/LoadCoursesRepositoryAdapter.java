// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.LoadCoursesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

import static tech.softhlon.learning.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoadFailed;
import static tech.softhlon.learning.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoaded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadCoursesRepositoryAdapter implements LoadCoursesRepository {
    private final CoursesJpaRepository coursesRepo;

    @Override
    public LoadCoursesResult execute() {
        try {
            var stream = StreamSupport.stream(coursesRepo.findAll().spliterator(), false);
            var courses = stream.map(this::toCourse).toList();
            return new CoursesLoaded(courses);
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CoursesLoadFailed(cause);
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
              entity.getVersion()
        );
    }
}

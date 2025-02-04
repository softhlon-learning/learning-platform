// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.CheckCourseRepository;
import tech.softhlon.learning.courses.domain.CheckCourseRepository.CheckCourseResult.CheckCourseFailed;
import tech.softhlon.learning.courses.domain.CheckCourseRepository.CheckCourseResult.CourseExists;
import tech.softhlon.learning.courses.domain.CheckCourseRepository.CheckCourseResult.CourseNotFound;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Check if given course exists repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckCourseRepositoryAdapter implements CheckCourseRepository {

    private final CoursesJpaRepository coursesRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public CheckCourseResult execute(
          UUID courseId) {

        try {
            return coursesRepo.existsById(courseId)
                  ? new CourseExists()
                  : new CourseNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckCourseFailed(cause);
        }

    }

}

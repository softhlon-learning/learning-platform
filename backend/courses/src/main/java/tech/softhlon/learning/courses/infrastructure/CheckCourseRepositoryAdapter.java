// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
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

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckCourseRepositoryAdapter implements CheckCourseRepository {
    private final CoursesJpaRepository coursesRepo;

    @Override
    public CheckCourseResult execute(CheckCourseRequest request) {
        try {
            return coursesRepo.existsById(request.id())
                  ? new CourseExists()
                  : new CourseNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckCourseFailed(cause);
        }
    }
}

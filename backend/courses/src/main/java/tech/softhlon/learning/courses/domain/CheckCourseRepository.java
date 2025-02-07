// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Check if given course exists repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CheckCourseRepository {

    /**
     * Check if given course exists in repository.
     * @param courseId Course Id
     * @return CheckCourseResult
     */
    CheckCourseResult execute(
          UUID courseId);

    /**
     * Check if given course exists in repository result.
     */
    sealed interface CheckCourseResult {
        record CourseExists() implements CheckCourseResult {}
        record CourseNotFound() implements CheckCourseResult {}
        record CheckCourseFailed(Throwable cause) implements CheckCourseResult {}
    }

}

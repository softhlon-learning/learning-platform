// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CheckCourseRepository {
    CheckCourseResult execute(CheckCourseRequest request);

    sealed interface CheckCourseResult {
        record CourseExists() implements CheckCourseResult {}
        record CourseNotFound() implements CheckCourseResult {}
        record CheckCourseFailed(Throwable cause) implements CheckCourseResult {}
    }
    record CheckCourseRequest(UUID id) {}
}

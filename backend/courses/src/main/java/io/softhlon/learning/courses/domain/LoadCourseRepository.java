// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadCourseRepository {
    LoadCourseResult execute(UUID id);

    record Account(
          UUID courseId,
          String name,
          String description,
          String content,
          String version) {}

    record Course(
          String name,
          String description) {}

    sealed interface LoadCourseResult {
        record CourseLoaded(Account account) implements LoadCourseResult {}
        record CourseLoadFailed(Throwable cause) implements LoadCourseResult {}
    }
}

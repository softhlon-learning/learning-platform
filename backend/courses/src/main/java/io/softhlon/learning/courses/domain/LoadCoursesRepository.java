// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadCoursesRepository {
    LoadCoursesResult execute();

    record Course(
          UUID courseId,
          int orderNo,
          String name,
          String description,
          String version) {}

    sealed interface LoadCoursesResult {
        record CoursesLoaded(List<Course> course) implements LoadCoursesResult {}
        record CoursesLoadFailed(Throwable cause) implements LoadCoursesResult {}
    }
}

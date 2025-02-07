// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load courses repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadCoursesRepository {

    /**
     * Load all available courses from repository.
     * @return LoadCoursesResult
     */
    LoadCoursesResult execute();

    /**
     * Load all available courses from repository result.
     */
    sealed interface LoadCoursesResult {
        record CoursesLoaded(List<Course> course) implements LoadCoursesResult {}
        record CoursesLoadFailed(Throwable cause) implements LoadCoursesResult {}
    }

    record Course(
          UUID courseId,
          String code,
          int orderNo,
          String name,
          String description,
          String content,
          String version) {}

}

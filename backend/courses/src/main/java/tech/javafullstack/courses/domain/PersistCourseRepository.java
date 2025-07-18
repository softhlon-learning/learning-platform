// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
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
 * Persist course repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface PersistCourseRepository {

    /**
     * Persist course in repository.
     * @param course Course to persist
     * @return PersistCourseResult
     */
    PersistCourseResult execute(
          PersistCourseRequest course);

    /**
     * Persist course in repository result.
     */
    sealed interface PersistCourseResult {
        record CoursePersisted() implements PersistCourseResult {}
        record CoursePersistenceFailed(Throwable cause) implements PersistCourseResult {}
    }

    record PersistCourseRequest(
          UUID id,
          String code,
          int orderNo,
          String name,
          String description,
          String content,
          String version) {}

}

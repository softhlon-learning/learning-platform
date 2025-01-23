// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface PersistCourseRepository {
    PersistCourseResult execute(PersistCourseRequest course);

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

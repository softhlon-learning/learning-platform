// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface PersistCourseRepository {
    PersistCourseResult execute(PersistCourseRequest course);

    record PersistCourseRequest(
          UUID id,
          String name,
          String description,
          String content,
          String version) {}

    sealed interface PersistCourseResult {
        record CoursePersisted() implements PersistCourseResult {}
        record CoursePersistenceFailed(Throwable cause) implements PersistCourseResult {}
    }
}

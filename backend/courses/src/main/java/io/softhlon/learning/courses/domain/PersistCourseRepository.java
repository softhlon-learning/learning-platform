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

interface PersistCourseRepository {
    PersistCourseResult execute(PersistCourseRequest course);

    record PersistCourseRequest(
          UUID courseId,
          String name,
          String description,
          String content,
          String version) {}

    sealed interface PersistCourseResult {
        record AccountPersisted() implements PersistCourseResult {}
        record AccountPersistenceFailed(Throwable cause) implements PersistCourseResult {}
    }
}

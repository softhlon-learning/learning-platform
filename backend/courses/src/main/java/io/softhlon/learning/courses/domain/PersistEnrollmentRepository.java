// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface PersistEnrollmentRepository {
    PersistEnrollmentResult execute(PersistEnrollmentRequest course);

    record PersistEnrollmentRequest(
          UUID courseId,
          UUID accountId,
          String status,
          String content,
          OffsetDateTime enrolledTime,
          OffsetDateTime completedTime) {}

    sealed interface PersistEnrollmentResult {
        record EnrollmentPersisted() implements PersistEnrollmentResult {}
        record EnrollmentPersistenceFailed(Throwable cause) implements PersistEnrollmentResult {}
    }
}

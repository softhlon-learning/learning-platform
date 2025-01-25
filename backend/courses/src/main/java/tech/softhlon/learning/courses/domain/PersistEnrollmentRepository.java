// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface PersistEnrollmentRepository {

    PersistEnrollmentResult execute(
          PersistEnrollmentRequest course);

    sealed interface PersistEnrollmentResult {
        record EnrollmentPersisted() implements PersistEnrollmentResult {}
        record EnrollmentNotPresentFoundFailed() implements PersistEnrollmentResult {}
        record EnrollmentPersistenceFailed(Throwable cause) implements PersistEnrollmentResult {}
    }

    record PersistEnrollmentRequest(
          UUID courseId,
          UUID accountId,
          String content,
          OffsetDateTime enrolledTime,
          OffsetDateTime completedTime) {}

}

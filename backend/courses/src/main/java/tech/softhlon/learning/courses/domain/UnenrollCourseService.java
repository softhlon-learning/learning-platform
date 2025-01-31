// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface UnenrollCourseService {

    Result execute(
          UUID accountId,
          UUID courseId);

    sealed interface Result {
        record Succeeded() implements Result {}
        record EnrollmentNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}

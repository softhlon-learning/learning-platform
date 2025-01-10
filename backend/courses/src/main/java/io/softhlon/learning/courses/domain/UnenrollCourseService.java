// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface UnenrollCourseService {
    Result unenroll(Request request);
    record Request(UUID accountId, UUID courseId) {}

    sealed interface Result {
        record Succeeded() implements Result {}
        record CourseNotFoundFailed(String message) implements Result {}
        record AccountNotEnrolledFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
}

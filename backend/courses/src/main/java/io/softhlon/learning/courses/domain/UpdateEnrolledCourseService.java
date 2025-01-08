// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface UpdateEnrolledCourseService {
    Result updateCourse(Request request);
    record Request(String accountId, String courseId, String content) {}

    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotEligibleFailed(String message) implements Result {}
        record CourseNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface GetCourseDetailsService {
    Result execute(Request request);

    sealed interface Result {
        record Succeeded(CourseDetails courseDetails) implements Result {}
        record CourseNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
    record Request(String accountId, String courseId) {}
    record CourseDetails(
          String courseId,
          String name,
          String description,
          String content,
          String version) {}
}

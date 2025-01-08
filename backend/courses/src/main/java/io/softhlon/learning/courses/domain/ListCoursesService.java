// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface ListCoursesService {
    Result listCourses(Request request);
    record Request(String accountId, String courseId) {}

    sealed interface Result {
        record Succeeded(List<Course> courses) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Course(
          String courseId,
          String name,
          String description) {}
}

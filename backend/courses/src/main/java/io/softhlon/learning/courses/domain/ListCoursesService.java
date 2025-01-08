// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface ListCoursesService {
    Result listCourses();
    record Request(String accountId, String courseId) {}

    sealed interface Result {
        record Succeeded(List<CourseView> courses) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record CourseView(
          UUID id,
          int orderNo,
          String name,
          String description,
          boolean enrolled) {}
}

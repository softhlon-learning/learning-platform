// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH- All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

interface JsonToCourseContentService {
    JsonToCourseContentResult execute(JsonToCourseContentRequest request);

    record JsonToCourseContentRequest(String json) {}
    sealed interface JsonToCourseContentResult {
        record JsonConverted(CourseContent content) implements JsonToCourseContentResult {}
        record JsonConvertFailed(Throwable cause) implements JsonToCourseContentResult {}
    }

    record CourseContent(List<Chapter> chapters) {}
    record Chapter(
          String name,
          List<Lecture> lectures) {}
    record Lecture(
          UUID id,
          String name,
          String type,
          boolean processed,
          String time,
          boolean selctected) {}
}

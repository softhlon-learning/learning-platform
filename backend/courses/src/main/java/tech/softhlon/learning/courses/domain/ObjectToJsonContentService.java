// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

interface ObjectToJsonContentService {
    ObjectToJsonContentResult execute(ObjectToJsonContentRequest request);

    record ObjectToJsonContentRequest(String json) {}
    sealed interface ObjectToJsonContentResult {
        record ObjectConvertedToJson(CourseContent content) implements ObjectToJsonContentResult {}
        record ObjectConvertFailedToJson(Throwable cause) implements ObjectToJsonContentResult {}
    }

    record CourseContent(
          List<Chapter> chapters) {}
    record Chapter(
          String name,
          List<Lecture> lectures) {}
    record Lecture(
          String id,
          String name,
          String type,
          boolean processed,
          String time,
          boolean selctected) {}
}

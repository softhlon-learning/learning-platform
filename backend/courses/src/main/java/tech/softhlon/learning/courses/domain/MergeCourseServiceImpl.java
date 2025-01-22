// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.CourseContent;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.JsonToCourseContentRequest;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.JsonToCourseContentResult.JsonConvertFailed;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.JsonToCourseContentResult.JsonConverted;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMergeFailed;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMerged;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class MergeCourseServiceImpl implements MergeCourseService {
    private final JsonToCourseContentService jsonToCourseContentService;
    @Override
    public MergeCourseResult execute(MergeCourseReuqest reuqest) {
        var result = jsonToCourseContentService.execute(
              new JsonToCourseContentRequest(reuqest.content()));

        return switch (result) {
            case JsonConverted(CourseContent content) -> processCourseContent(content);
            case JsonConvertFailed(Throwable cause) -> new CourseMergeFailed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private MergeCourseResult processCourseContent(CourseContent content) {
        return new CourseMerged();
    }
}


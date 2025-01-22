// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMerged;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.CourseContent;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.JsonToCourseContentRequest;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class MergeCourseServiceImpl implements MergeCourseService {
    private final JsonToCourseContentService jsonToCourseContentService;

    @Override
    public MergeCourseResult execute(MergeCourseReuqest reuqest) {
        jsonToCourseContentService.execute(new JsonToCourseContentRequest(reuqest.content()));
        return new CourseMerged();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CourseContent courseContent(String courseContent) {
        return null;
    }
}


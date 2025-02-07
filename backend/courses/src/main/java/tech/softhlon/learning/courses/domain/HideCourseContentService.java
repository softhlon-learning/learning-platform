// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.javafullstack.courses.domain.ContentService.Chapter;
import tech.javafullstack.courses.domain.ContentService.CourseContent;
import tech.javafullstack.courses.domain.ContentService.Lecture;

import java.util.ArrayList;
import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Hide course content service implementation.
 */
@Service
@RequiredArgsConstructor
class HideCourseContentService {
    private final ContentService contentService;

    /**
     * Hide course content (for lecture with preview false flag).
     * @param contentJson Course content JSON
     * @return Updated course content JSON
     */
    String execute(String contentJson) {
        var courseContent = contentService.jsonToCurseContent(contentJson);

        List<Chapter> newChapters = new ArrayList<>();
        for (Chapter chapter : courseContent.chapters()) {
            List<Lecture> newLectures = new ArrayList<>();
            for (Lecture lecture : chapter.lectures()) {
                if (!lecture.preview()) {
                    var newLecture = new Lecture(
                          null,
                          lecture.name(),
                          lecture.type(),
                          lecture.preview(),
                          lecture.processed(),
                          lecture.time(),
                          lecture.selected()
                    );
                    newLectures.add(newLecture);
                } else {
                    newLectures.add(lecture);
                }
            }
            var newChapter = new Chapter(chapter.name(), newLectures);
            newChapters.add(newChapter);
        }

        var updatedCourseContent = new CourseContent(newChapters);
        return contentService.courseContentToJson(updatedCourseContent);

    }

}

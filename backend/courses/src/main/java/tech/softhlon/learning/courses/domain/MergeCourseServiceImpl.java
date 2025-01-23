// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.ContentService.Chapter;
import tech.softhlon.learning.courses.domain.ContentService.CourseContent;
import tech.softhlon.learning.courses.domain.ContentService.Lecture;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.Enrollment;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsRequest;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentLoadFailed;
import tech.softhlon.learning.courses.domain.LoadEnrollmentsRepository.ListEnrollmentsResult.EnrollmentsLoaded;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMergeFailed;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMerged;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentRequest;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersisted;
import tech.softhlon.learning.courses.domain.PersistEnrollmentRepository.PersistEnrollmentResult.EnrollmentPersistenceFailed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class MergeCourseServiceImpl implements MergeCourseService {
    private final ContentService contentService;
    private final LoadEnrollmentsRepository loadEnrollmentsRepository;
    private final PersistEnrollmentRepository persistEnrollmentRepository;

    @Override
    public MergeCourseResult execute(MergeCourseReuqest reuqest) {
        try {
            var content = contentService.jsonToCurseContent(reuqest.content());
            return processCourseContent(reuqest, content);
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CourseMergeFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private MergeCourseResult processCourseContent(
          MergeCourseReuqest reuqest,
          CourseContent content) {

        var result = loadEnrollmentsRepository.execute(
              new ListEnrollmentsRequest(reuqest.courseId()));

        return switch (result) {
            case EnrollmentLoadFailed(Throwable cause) -> new CourseMergeFailed(cause);
            case EnrollmentsLoaded(List<Enrollment> enrollments) -> processEnrollments(reuqest, content, enrollments);
        };
    }

    private MergeCourseResult processEnrollments(
          MergeCourseReuqest reuqest,
          CourseContent content,
          List<Enrollment> enrollments) {

        for (Enrollment enrollment : enrollments) {
            var enrollmentContent = contentService.jsonToCurseContent(enrollment.content());
            var updatedContent = updateContent(content, enrollmentContent);
            persistEnrollment(reuqest, enrollment, updatedContent);

        }
        return new CourseMerged();
    }

    private CourseContent updateContent(
          CourseContent content,
          CourseContent enrollmentContent) {
        Map<String, Lecture> idToLectureMap = new HashMap<>();

        // index all lectures in enrollment
        for (Chapter chaper : enrollmentContent.chapters()) {
            for (Lecture lecture : chaper.lectures()) {
                idToLectureMap.put(lecture.id(), lecture);
            }
        }

        // create/update processed flag in new content
        List<Chapter> chaptersCopy = new ArrayList<>();
        for (Chapter chaper : content.chapters()) {
            List<Lecture> lecturesCopy = new ArrayList<>();
            for (Lecture lecture : chaper.lectures()) {
                boolean processed = false;
                boolean selected = false;
                if (idToLectureMap.containsKey(lecture.id())) {
                    if (idToLectureMap.get(lecture.id()).processed()) {
                        processed = true;
                    }
                    if (idToLectureMap.get(lecture.id()).selected()) {
                        selected = true;
                    }
                }
                lecturesCopy.add(
                      new Lecture(
                            lecture.id(),
                            lecture.name(),
                            lecture.type(),
                            processed,
                            lecture.time(),
                            selected));
            }
            chaptersCopy.add(new Chapter(chaper.name(), lecturesCopy));
        }
        return new CourseContent(chaptersCopy);
    }

    MergeCourseResult persistEnrollment(
          MergeCourseReuqest reuqest,
          Enrollment enrollment,
          CourseContent enrollmentContent) {

        var result = persistEnrollmentRepository.execute(
              new PersistEnrollmentRequest(
                    reuqest.courseId(),
                    enrollment.accountId(),
                    contentService.courseContentToJson(enrollmentContent),
                    enrollment.enrolledTime(),
                    enrollment.completedTime()));

        return switch (result) {
            case EnrollmentPersisted enrollmentPersisted -> new CourseMerged();
            case EnrollmentPersistenceFailed(Throwable cause) -> new CourseMergeFailed(cause);
        };
    }
}

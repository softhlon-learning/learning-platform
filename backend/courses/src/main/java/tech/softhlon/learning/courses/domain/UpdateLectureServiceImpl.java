// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.ContentService.CourseContent;
import tech.softhlon.learning.courses.domain.ContentService.Chapter;
import tech.softhlon.learning.courses.domain.ContentService.Lecture;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.Enrollment;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoadFailed;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentLoaded;
import tech.softhlon.learning.courses.domain.LoadEnrollmentRepository.LoadEnrollmentResult.EnrollmentNotFoundInDatabase;
import tech.softhlon.learning.courses.domain.UpdateLectureService.Result.Failed;
import tech.softhlon.learning.courses.domain.UpdateLectureService.Result.LectureNotFoundFailed;

import java.util.ArrayList;
import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class UpdateLectureServiceImpl implements UpdateLectureService {

    private final LoadEnrollmentRepository loadEnrollmentRepository;
    private final ContentService contentService;

    @Override
    public Result execute(
          Request request) {

        var result = loadEnrollmentRepository.execute(
              request.accountId(),
              request.courseId());

        return switch (result) {
            case EnrollmentLoaded(Enrollment enrollment) -> processLectureUpdate(request, enrollment);
            case EnrollmentNotFoundInDatabase() -> new LectureNotFoundFailed();
            case EnrollmentLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result processLectureUpdate(
          Request request,
          Enrollment enrollment) {

       return null;

    }

    private CourseContent updateContent(
          Request request,
          Enrollment enrollment) {

        var enrollmentContent = contentService.jsonToCurseContent(
              enrollment.content());

        // create/update processed flag in new content
        List<Chapter> chaptersCopy = new ArrayList<>();
        for (Chapter chaper : enrollmentContent.chapters()) {
            for (Lecture lecture : chaper.lectures()) {
                List<Lecture> lecturesCopy = new ArrayList<>();
                if (lecture.id().equals(request.lectureId())) {
                    lecturesCopy.add(
                          new Lecture(
                                lecture.id(),
                                lecture.name(),
                                lecture.type(),
                                request.processed(),
                                lecture.time(),
                                true));
                } else {
                    lecturesCopy.add(lecture);
                }

                chaptersCopy.add(new Chapter(chaper.name(), lecturesCopy));
            }
        }

       return new CourseContent(chaptersCopy);

    }

}

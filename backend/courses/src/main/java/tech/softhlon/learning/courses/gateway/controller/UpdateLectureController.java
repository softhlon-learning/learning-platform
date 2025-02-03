// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.softhlon.learning.common.hexagonal.RestApiAdapter;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.courses.domain.UpdateLectureService;
import tech.softhlon.learning.courses.domain.UpdateLectureService.Request;
import tech.softhlon.learning.courses.domain.UpdateLectureService.Result.Failed;
import tech.softhlon.learning.courses.domain.UpdateLectureService.Result.LectureNotFoundFailed;
import tech.softhlon.learning.courses.domain.UpdateLectureService.Result.Succeeded;

import java.util.UUID;

import static tech.softhlon.learning.common.controller.ResponseBodyHelper.*;
import static tech.softhlon.learning.common.text.IdPrinter.printShort;
import static tech.softhlon.learning.courses.gateway.controller.RestResources.UPDATE_LECTURE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class UpdateLectureController {
    private final UpdateLectureService service;
    private final HttpServletRequest httpRequest;
    private final AuthenticationContext authContext;

    /**
     * PATCH /api/v1/course/{courseId}/enrollment/lecture.
     */
    @PatchMapping(UPDATE_LECTURE)
    ResponseEntity<?> updateLecture(
          @PathVariable("courseId") UUID courseId,
          @Validated @RequestBody UpdateLectureRequest request) {

        var accountId = authContext.accountId();
        log.info("controller | request / Update lecture, courseId: {}, {}",
              printShort(courseId),
              request);

        var result = service.execute(
              prepareRequest(courseId, request));

        return switch (result) {
            case Succeeded succeeded -> successOkBody();
            case LectureNotFoundFailed() -> badRequestBody(httpRequest, null);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    record UpdateLectureRequest(
          String lectureId,
          boolean processed) {

        @Override
        public String toString() {
            return """
                  [lectureId: %s, processed: %s]"""
                  .formatted(
                        lectureId,
                        processed);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Request prepareRequest(
          UUID courseId,
          UpdateLectureRequest updateEnrollmentRequest) {

        return new Request(
              authContext.accountId(),
              courseId,
              updateEnrollmentRequest.lectureId(),
              updateEnrollmentRequest.processed());

    }

}

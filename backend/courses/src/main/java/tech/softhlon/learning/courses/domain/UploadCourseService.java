// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Upload course service interface.
 */
@InboundPort
@FunctionalInterface
public interface UploadCourseService {

    /**
     * Upload course.
     * @param request Operation request
     * @return Result
     */
    Result execute(
          Request request);

    /**
     * Upload course result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Request(
          UUID courseId,
          String code,
          int orderNo,
          String name,
          String description,
          String content,
          String version) {}

}

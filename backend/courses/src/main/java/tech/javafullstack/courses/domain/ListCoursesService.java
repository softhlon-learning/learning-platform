// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * List courses service interface.
 */
@InboundPort
@FunctionalInterface
public interface ListCoursesService {

    /**
     * List all available courses.
     * @param accountId Account Id
     * @return Result
     */
    Result execute(
          UUID accountId);

    /**
     * List all available courses result.
     */
    sealed interface Result {
        record Succeeded(CoursesView courses) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record CoursesView(
          List<CourseView> courseList,
          SubcriptionType subcriptionType) {
    }

    record CourseView(
          UUID id,
          String code,
          int orderNo,
          String name,
          String description,
          String content,
          boolean enrolled) {}

    enum SubcriptionType {
        FREE_TRIAL,
        SUBSCRIBED,
        NOT_SUBSCRIBED
    }
}

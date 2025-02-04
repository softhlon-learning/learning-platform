package tech.softhlon.learning.courses.domain;// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Convert from/to json to CourseContent service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class ContentService {

    /**
     * Convert from JSON to CourseContent.
     * @param json JSON (base64)
     * @return CourseContent
     */
    CourseContent jsonToCurseContent(
          String json) {

        return toCourseContent(
              base64Decode(json));

    }

    /**
     * Cponvert from CourseContent to JSON.
     * @param content CourseContent
     * @return JSON (base64)
     */
    String courseContentToJson(
          CourseContent content) {

        return base64Encode(
              toJson(content));

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
          boolean preview,
          boolean processed,
          String time,
          boolean selected) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CourseContent toCourseContent(
          String json) {

        return new Gson()
              .fromJson(
                    json,
                    CourseContent.class
              );

    }

    private String toJson(
          CourseContent content) {

        return new Gson().
              toJson(content);

    }

    private String base64Decode(
          String value) {

        var decodedBytes = Base64.
              getDecoder()
              .decode(value);

        return new String(decodedBytes);

    }

    private String base64Encode(
          String value) {

        return Base64
              .getEncoder()
              .encodeToString(value.getBytes());

    }

}

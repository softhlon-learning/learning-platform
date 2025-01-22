package tech.softhlon.learning.courses.domain;// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.JsonToCourseContentService.JsonToCourseContentResult.JsonConvertFailed;

import java.util.Base64;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class JsonToCourseContentServiceImpl implements JsonToCourseContentService {
    @Override
    public JsonToCourseContentResult execute(JsonToCourseContentRequest request) {
        try {
            var json = base64Decode(request.json());
            log.info("Course json: {}", json);
            return new JsonToCourseContentResult.JsonConverted(courseContent(json));
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new JsonConvertFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private String base64Decode(String value) {
        var decodedBytes = Base64.getDecoder().decode(value);
        return new String(decodedBytes);
    }

    private CourseContent courseContent(String json) {
        return new Gson().fromJson(json, CourseContent.class);
    }
}

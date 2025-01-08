// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.softhlon.learning.courses.domain.UploadCourseService;
import io.softhlon.learning.courses.domain.UploadCourseService.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Component
public class CreateCoursesOperator {
    private final UploadCourseService uploadCourseService;
    private final CourseDefinitions courseDefinitions;
    private final ObjectMapper mapper;

    public CreateCoursesOperator(
          UploadCourseService uploadCourseService,
          CourseDefinitions courseDefinitions,
          ObjectMapper mapper) {
        this.uploadCourseService = uploadCourseService;
        this.courseDefinitions = courseDefinitions;
        var objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper = objectMapper;
    }

    public void execute() throws IOException {
        log.info("Create Courses operator started");
        createCourse(courseDefinitions.getApiDesignDefinition());
        createCourse(courseDefinitions.getArchitectureDefinition());
        createCourse(courseDefinitions.getDomainDrovenDesignDefinition());
        createCourse(courseDefinitions.getJavaCoreDefinition());
        createCourse(courseDefinitions.getMessagingDefinition());
        createCourse(courseDefinitions.getMicroservicesDefinition());
        createCourse(courseDefinitions.getSpringDefinition());
        createCourse(courseDefinitions.getSqlDatasesDefinition());
        createCourse(courseDefinitions.getNoSqlDatabasesDefinition());
        log.info("Create Courses operator finished");
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void createCourse(Resource resource) throws IOException {
        log.info("Creating/Updating course from definition: {}", resource.getFilename());
        var content = resource.getContentAsByteArray();
        var courseDefinition = mapper.readValue(content, CourseDefinition.class);
        uploadCourseService.execute(prepareRequest(courseDefinition, content));
    }

    private record CourseDefinition(
          UUID id,
          String name,
          String description,
          String version) {}

    private Request prepareRequest(CourseDefinition courseDefinition, byte[] content) {
        return new Request(
              courseDefinition.id(),
              courseDefinition.name(),
              courseDefinition.description(),
              toBase64(content),
              courseDefinition.version()
        );
    }

    private String toBase64(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }
}

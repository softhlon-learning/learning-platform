// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import tech.softhlon.learning.courses.domain.UploadCourseService;
import tech.softhlon.learning.courses.domain.UploadCourseService.Request;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create/upload courses operator.
 */
@Slf4j
@Component
public class CreateCoursesOperator {

    private static final String ACCOUNT = "account";
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
        objectMapper.configure(
              DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
              false
        );
        this.mapper = objectMapper;

    }

    /**
     * Create/upload all courses
     * @throws IOException
     */
    public void execute() throws IOException {

        MDC.put(ACCOUNT,
              "initializer");

        log.info("operator | Create Courses operator started");
        createCourse(courseDefinitions.getApiDesignDefinition());
        createCourse(courseDefinitions.getArchitectureDefinition());
        createCourse(courseDefinitions.getDomainDrovenDesignDefinition());
        createCourse(courseDefinitions.getJavaCoreDefinition());
        createCourse(courseDefinitions.getMessagingDefinition());
        createCourse(courseDefinitions.getMicroservicesDefinition());
        createCourse(courseDefinitions.getSpringDefinition());
        createCourse(courseDefinitions.getAngularDefinition());
        log.info("operator | Create Courses operator finished");

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void createCourse(
          Resource resource) throws IOException {

        log.info("operator | Creating/Updating course from definition: {}",
              resource.getFilename());

        var content = resource.getContentAsByteArray();

        var courseDefinition = mapper.readValue(
              content,
              CourseDefinition.class);

        uploadCourseService.execute(prepareRequest(
              courseDefinition,
              content)
        );

    }

    private Request prepareRequest(
          CourseDefinition courseDefinition,
          byte[] content) throws JsonProcessingException {

        return new Request(
              courseDefinition.id(),
              courseDefinition.code(),
              courseDefinition.orderNo(),
              courseDefinition.name(),
              courseDefinition.description(),
              toBase64(content),
              courseDefinition.version()
        );

    }

    private String toBase64(
          byte[] content) throws JsonProcessingException {

        return Base64
              .getEncoder()
              .encodeToString(
                    convertToJson(
                          new String(
                                content,
                                Charset.defaultCharset()
                          ))
              );
    }

    private byte[] convertToJson(
          String yamlString) throws JsonProcessingException {

        var yamlMapper = new ObjectMapper(new YAMLFactory());
        var jsonNode = yamlMapper.readTree(yamlString);
        var jsonMapper = new ObjectMapper();

        return jsonMapper
              .writeValueAsString(jsonNode)
              .getBytes();

    }

    private record CourseDefinition(
          UUID id,
          String code,
          int orderNo,
          String name,
          String description,
          String version) {}

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.softhlon.learning.courses.domain.UploadCourseService;
import io.softhlon.learning.courses.domain.UploadCourseService.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateCoursesOperator {
    private final UploadCourseService uploadCourseService;

    @Value("classpath:courses/api-design.yml")
    private Resource apiDesignDefinition;

    @Value("classpath:courses/architecture.yml")
    private Resource architectureDefinition;

    @Value("classpath:courses/domain-driven-design.yml")
    private Resource domainDrovenDesignDefinition;

    @Value("classpath:courses/java-core.yml")
    private Resource javaCoreDefinition;

    @Value("classpath:courses/messaging.yml")
    private Resource messagingDefinition;

    @Value("classpath:courses/messaging.yml")
    private Resource microservicesDefinition;

    @Value("classpath:courses/spring.yml")
    private Resource springDefinition;

    @Value("classpath:courses/sql-databases.yml")
    private Resource sqlDatasesDefinition;

    @Value("classpath:courses/no-sql-databases.yml")
    private Resource noSqlDatabasesDefinition;

    public void execute() throws IOException {
        log.info("Create Courses operator started");
        createCourse(apiDesignDefinition);
        createCourse(architectureDefinition);
        createCourse(domainDrovenDesignDefinition);
        createCourse(javaCoreDefinition);
        createCourse(messagingDefinition);
        createCourse(microservicesDefinition);
        createCourse(springDefinition);
        createCourse(sqlDatasesDefinition);
        createCourse(noSqlDatabasesDefinition);
        log.info("Create Courses operator finished");
    }

    private void createCourse(Resource resource) throws IOException {
        log.info("Creating/Updating course from definition: {}", resource.getFilename());
        var mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var content = resource.getContentAsByteArray();
        var courseDefinition = mapper.readValue(content, CourseDefinition.class);
        var request = prepareRequest(courseDefinition, content);
        uploadCourseService.execute(request);
    }

    private record CourseDefinition(
          UUID id,
          String name,
          String description,
          String version) {}

    private Request prepareRequest(CourseDefinition courseDefinition, byte [] content) {
        return new Request(
              courseDefinition.id(),
              courseDefinition.name(),
              courseDefinition.description(),
              Base64.getEncoder().encodeToString(content),
              courseDefinition.version()
        );
    }
}

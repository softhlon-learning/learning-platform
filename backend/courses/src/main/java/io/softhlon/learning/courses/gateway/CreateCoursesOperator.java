// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Component
public class CreateCoursesOperator {
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

    public void execute() {
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

    private void createCourse(Resource resource) {
        log.info("Creating/Updating course from definition: {}", resource.getFilename());
    }
}

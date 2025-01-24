// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Getter
@Component
class CourseDefinitions {

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

    @Value("classpath:courses/microservices.yml")
    private Resource microservicesDefinition;

    @Value("classpath:courses/spring.yml")
    private Resource springDefinition;

    @Value("classpath:courses/fullstack.yml")
    private Resource fullstackDefinition;

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import io.softhlon.learning.courses.gateway.GatewayConfiguration;
import io.softhlon.learning.courses.application.ApplicationConfiguration;
import io.softhlon.learning.courses.infrastructure.InfrastructureConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static io.softhlon.learning.courses.CoursesConfiguration.PACKAGE_TO_SCAN;

@Configuration(proxyBeanMethods = false)
@ComponentScan(PACKAGE_TO_SCAN)
@Import({
      GatewayConfiguration.class,
      ApplicationConfiguration.class,
      InfrastructureConfiguration.class
})
public class CoursesConfiguration {
    public static final String PACKAGE_TO_SCAN = "io.softhlon.learning.courses";
    public static final String MODULE_PREFIX = "Courses";
}

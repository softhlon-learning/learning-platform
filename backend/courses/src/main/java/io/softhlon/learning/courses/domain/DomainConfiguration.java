// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.courses.CoursesConfiguration;
import org.springframework.context.annotation.Configuration;

import static io.softhlon.learning.courses.domain.DomainConfiguration.BeanNames.APPLICATION_CONFIGURATION;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Configuration(
      value = APPLICATION_CONFIGURATION,
      proxyBeanMethods = false)
public class DomainConfiguration {
    static class BeanNames {
        public static final String APPLICATION_CONFIGURATION =
              CoursesConfiguration.MODULE_PREFIX + "DomainConfiguration";
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.infrastructure;

import io.softhlon.learning.subscriptions.SubscriptionsConfiguration;
import org.springframework.context.annotation.Configuration;

import static io.softhlon.learning.subscriptions.infrastructure.InfrastructureConfiguration.BeanNames.APPLICATION_CONFIGURATION;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Configuration(
      value = APPLICATION_CONFIGURATION,
      proxyBeanMethods = false)
public class InfrastructureConfiguration {
    static class BeanNames {
        public static final String APPLICATION_CONFIGURATION =
              SubscriptionsConfiguration.MODULE_PREFIX + "InfrastructureConfiguration";
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

import io.softhlon.learning.subscriptions.SubscriptionsConfiguration;
import org.springframework.context.annotation.Configuration;

import static io.softhlon.learning.subscriptions.domain.DomainConfiguration.BeanNames.APPLICATION_CONFIGURATION;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Configuration(
      value = APPLICATION_CONFIGURATION,
      proxyBeanMethods = false)
public class DomainConfiguration {
    static class BeanNames {
        public static final String APPLICATION_CONFIGURATION =
              SubscriptionsConfiguration.MODULE_PREFIX + "DomainConfiguration";
    }
}

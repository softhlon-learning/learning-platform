// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import io.softhlon.learning.subscriptions.application.ApplicationConfiguration;
import io.softhlon.learning.subscriptions.gateway.GatewayConfiguration;
import io.softhlon.learning.subscriptions.infrastructure.InfrastructureConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static io.softhlon.learning.subscriptions.SubscriptionsConfiguration.PACKAGE_TO_SCAN;

@Configuration(proxyBeanMethods = false)
@ComponentScan(PACKAGE_TO_SCAN)
@Import({
      GatewayConfiguration.class,
      ApplicationConfiguration.class,
      InfrastructureConfiguration.class
})
public class SubscriptionsConfiguration {
    public static final String PACKAGE_TO_SCAN = "io.softhlon.learning.subscriptions";
    public static final String MODULE_PREFIX = "Subscriptions";
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.gateway;

import org.springframework.context.annotation.Configuration;

import static io.softhlon.learning.accounts.AccountsConfiguration.MODULE_PREFIX;
import static io.softhlon.learning.accounts.gateway.GatewayConfiguration.BeanNames.APPLICATION_CONFIGURATION;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Configuration(
      value = APPLICATION_CONFIGURATION,
      proxyBeanMethods = false)
public class GatewayConfiguration {
    static class BeanNames {
        public static final String APPLICATION_CONFIGURATION =
              MODULE_PREFIX + "GatewayConfiguration";
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts;

import io.softhlon.learning.accounts.gateway.GatewayConfiguration;
import io.softhlon.learning.accounts.infrastructure.InfrastructureConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static io.softhlon.learning.accounts.AccountsConfiguration.PACKAGE_TO_SCAN;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Configuration(proxyBeanMethods = false)
@ComponentScan(PACKAGE_TO_SCAN)
@Import({
      GatewayConfiguration.class,
      InfrastructureConfiguration.class
})
public class AccountsConfiguration {
    public static final String PACKAGE_TO_SCAN = "io.softhlon.learning.accounts";
    public static final String MODULE_PREFIX = "Accounts";
}

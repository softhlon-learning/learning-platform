// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.PMARAT - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.application;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import io.softhlon.learning.accounts.AccountsConfiguration;
import io.softhlon.learning.courses.CoursesConfiguration;
import io.softhlon.learning.subscriptions.SubscriptionsConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Configuration(proxyBeanMethods = false)
@Import({
      AccountsConfiguration.class,
      CoursesConfiguration.class,
      SubscriptionsConfiguration.class
})
class ApplicationConfiguration {
}

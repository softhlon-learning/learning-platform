// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Events log JPA repository interface.
 */
interface EventLogJpaRepository extends CrudRepository<EventLogEntity, UUID> {
}

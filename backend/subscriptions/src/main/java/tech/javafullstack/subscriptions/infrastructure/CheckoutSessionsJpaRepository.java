// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Checkout sessions JPA repository interface.
 */
interface CheckoutSessionsJpaRepository extends CrudRepository<CheckoutSessionEntity, UUID> {

    Optional<CheckoutSessionEntity> findBySessionId(
          String sessionId);

}

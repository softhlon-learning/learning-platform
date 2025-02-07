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
 * Customers JPA repository interface.
 */
interface CustomersJpaRepository extends CrudRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByCustomerId(
          String customerId);

    Optional<CustomerEntity> findByAccountId(
          UUID accountId);

}

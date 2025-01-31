// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

interface CustomersJpaRepository extends CrudRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByCustomerId(
          String customerId);

    Optional<CustomerEntity> findByAccountId(
          UUID accountId);

}

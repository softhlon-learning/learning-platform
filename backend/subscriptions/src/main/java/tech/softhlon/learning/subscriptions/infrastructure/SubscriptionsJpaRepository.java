// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Repository
interface SubscriptionsJpaRepository extends CrudRepository<SubscriptionEntity, UUID> {

    Optional<SubscriptionEntity> findBySubscriptionId(
          String subscriptionId);

    @Query(value = """
          SELECT s.* FROM subscriptions s 
          JOIN customers c ON c.customer_id = s.customer_id
          """, nativeQuery = true)
    Optional<SubscriptionEntity> findByAccountId(
          UUID accountId);
}

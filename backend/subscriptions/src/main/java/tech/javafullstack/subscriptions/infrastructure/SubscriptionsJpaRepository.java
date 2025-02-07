// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Subscriptions JPA repository interface.
 */
@Repository
interface SubscriptionsJpaRepository extends CrudRepository<SubscriptionEntity, UUID> {

    Optional<SubscriptionEntity> findBySubscriptionId(
          String subscriptionId);

    @Query(value = """
          SELECT s.* FROM _subscriptions.subscriptions s 
          JOIN _subscriptions.customers c ON c.customer_id = s.customer_id
          WHERE s.active = true AND c.account_id =?1
          """, nativeQuery = true)
    Optional<SubscriptionEntity> findByAccountId(
          UUID accountId);

    @Query(value = """
              UPDATE _subscriptions.subscriptions 
              SET active = false WHERE cancel_at < now()
          """,
          nativeQuery = true)
    @Modifying
    void deactivateCanceledSubscriptions();

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Free tials JPA repository interface.
 */
interface FreeTrialsJpaRepository extends CrudRepository<FreeTrialEntity, UUID> {

    @Query(name = """
          SELECT f.* FROM _subscriptions.free_trials f
          WHERE f.account_id = :accountId AND f.expire_at < now() 
          """ , nativeQuery = true)
    Optional<FreeTrialEntity> findActive(@Param("accountId") UUID accountId);

}

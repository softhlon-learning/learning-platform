// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Accounts JPA repository interface.
 */
@Repository
interface AccountsJpaRepository extends CrudRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByEmail(
          String email);

    boolean existsByEmail(
          String email);

    @Query(value = """
          UPDATE _accounts.accounts 
          SET is_active = :active WHERE account_id = :accountId
          """,
          nativeQuery = true)
    @Modifying
    void updateIsActive(
          @Param(":accountId") UUID accountId,
          @Param(":active") boolean isActice);

}

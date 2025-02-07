// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Invoices JPA repository interface.
 */
interface InvoicesJpaRepository extends CrudRepository<InvoiceEntity, UUID> {

    @Query(value = """
              UPDATE _subscriptions.subscriptions 
              SET active = true WHERE invoice_id = :invoiceId
          """,
          nativeQuery = true)
    @Modifying
    void avtivatePaidSubscription(@Param("invoiceId") String invoiceId);

}

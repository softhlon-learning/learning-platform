// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

interface InvoicesJpaRepository extends CrudRepository<InvoiceEntity, UUID> {

    @Query(value = """
              UPDATE _subscriptions.subscriptions 
              SET active = true WHERE invoice_id = :invoiceId
          """,
          nativeQuery = true)
    @Modifying
    void avtivatePaidSubscription(@Param("invoiceId") String invoiceId);

}

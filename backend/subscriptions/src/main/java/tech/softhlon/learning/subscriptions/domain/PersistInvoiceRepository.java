// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.OutboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist invoice repository interface.
 */
@OutboundPort
@FunctionalInterface
public interface PersistInvoiceRepository {

    /**
     * Persist invoice in repository.
     * @param request Operation request
     * @return PersistInvoiceResult
     */
    PersistInvoiceResult execute(
          PersistInvoiceRequest request);

    /**
     * Persist invoice in repository result.
     */
    sealed interface PersistInvoiceResult {
        record InvoicePersisted() implements PersistInvoiceResult {}
        record InvoicePersistenceFailed(Throwable cause) implements PersistInvoiceResult {}
    }

    record PersistInvoiceRequest(
          String invoiceId,
          boolean paid) {}

}

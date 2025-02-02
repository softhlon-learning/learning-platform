// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.OutboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@FunctionalInterface
public interface PersistInvoiceRepository {

    PersistInvoiceResult execute(
          PersistInvoiceRequest request);

    sealed interface PersistInvoiceResult {
        record InvoicePersisted() implements PersistInvoiceResult {}
        record InvoicePersistenceFailed(Throwable cause) implements PersistInvoiceResult {}
    }

    record PersistInvoiceRequest(
          String invoiceId,
          boolean paid) {}

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.PersistInvoiceRepository;
import tech.softhlon.learning.subscriptions.domain.PersistInvoiceRepository.PersistInvoiceResult.InvoicePersisted;
import tech.softhlon.learning.subscriptions.domain.PersistInvoiceRepository.PersistInvoiceResult.InvoicePersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class PersistInvoiceRepositoryAdapter implements PersistInvoiceRepository {
    private final InvoicesJpaRepository invoicesJpaRepository;

    @Override
    public PersistInvoiceResult execute(PersistInvoiceRequest request) {

        try {
            invoicesJpaRepository.save(
                  entity(request));
            return new InvoicePersisted();

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new InvoicePersistenceFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private InvoiceEntity entity(PersistInvoiceRequest request) {
        return InvoiceEntity.builder()
              .invoiceId(request.invoiceId())
              .paid(request.paid())
              .build();
    }

}

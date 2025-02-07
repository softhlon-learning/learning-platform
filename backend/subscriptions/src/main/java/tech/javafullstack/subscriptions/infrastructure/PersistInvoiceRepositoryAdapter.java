// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.PersistInvoiceRepository;
import tech.javafullstack.subscriptions.domain.PersistInvoiceRepository.PersistInvoiceResult.InvoicePersisted;
import tech.javafullstack.subscriptions.domain.PersistInvoiceRepository.PersistInvoiceResult.InvoicePersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist invoice repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class PersistInvoiceRepositoryAdapter implements PersistInvoiceRepository {
    private final InvoicesJpaRepository invoicesJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistInvoiceResult execute(PersistInvoiceRequest request) {

        try {

            invoicesJpaRepository.save(entity(request));
            invoicesJpaRepository.avtivatePaidSubscription(request.invoiceId());
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

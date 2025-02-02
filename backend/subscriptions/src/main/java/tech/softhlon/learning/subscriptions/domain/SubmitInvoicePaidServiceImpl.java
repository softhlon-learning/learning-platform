// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.PersistInvoiceRepository.PersistInvoiceRequest;
import tech.softhlon.learning.subscriptions.domain.PersistInvoiceRepository.PersistInvoiceResult.InvoicePersisted;
import tech.softhlon.learning.subscriptions.domain.PersistInvoiceRepository.PersistInvoiceResult.InvoicePersistenceFailed;
import tech.softhlon.learning.subscriptions.domain.SubmitInvoicePaidService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitInvoicePaidService.Result.Succeeded;

import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.invoiceId;
import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.paid;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class SubmitInvoicePaidServiceImpl implements SubmitInvoicePaidService {
    private final String webhookSecret;
    private final PersistInvoiceRepository persistInvoiceRepository;

    public SubmitInvoicePaidServiceImpl(
          @Value("${stripe.invoice-paid.webhook.secret}") String webhookSecret,
          PersistInvoiceRepository persistInvoiceRepository) {

        this.webhookSecret = webhookSecret;
        this.persistInvoiceRepository = persistInvoiceRepository;

    }

    @Override
    public Result execute(
          String sigHeader,
          String payload) {

        try {
            var event = Webhook.constructEvent(
                  payload,
                  sigHeader,
                  webhookSecret);
            var invoiceId = invoiceId(event);
            var paid = paid(event);
            var result = persistInvoiceRepository.execute(
                  new PersistInvoiceRequest(
                        invoiceId,
                        paid));

            return switch (result) {
                case InvoicePersisted() -> new Succeeded();
                case InvoicePersistenceFailed(Throwable cause) -> new Failed(cause);
            };

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new Failed(cause);
        }
    }

}

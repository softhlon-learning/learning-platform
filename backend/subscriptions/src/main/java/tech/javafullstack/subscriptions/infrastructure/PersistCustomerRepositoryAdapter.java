// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.PersistCustomerRepository;
import tech.javafullstack.subscriptions.domain.PersistCustomerRepository.PersistCustomerResult.CustomerPersisted;
import tech.javafullstack.subscriptions.domain.PersistCustomerRepository.PersistCustomerResult.CustomerPersistenceFailed;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist Stripe customer repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class PersistCustomerRepositoryAdapter implements PersistCustomerRepository {
    private final CustomersJpaRepository customersJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistCustomerResult execute(
          UUID id,
          String customerId,
          UUID accountId) {

        try {

            customersJpaRepository.save(
                  CustomerEntity.builder()
                        .id(id)
                        .customerId(customerId)
                        .accountId(accountId)
                        .build()
            );
            return new CustomerPersisted();

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CustomerPersistenceFailed(cause);
        }

    }

}

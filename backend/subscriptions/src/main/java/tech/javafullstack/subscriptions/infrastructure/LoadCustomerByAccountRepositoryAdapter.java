// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.LoadCustomerByAccountRepository;
import tech.javafullstack.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerLoadFailed;
import tech.javafullstack.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerLoaded;
import tech.javafullstack.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerNotFound;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * LoadCustomerByAccountRepository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class LoadCustomerByAccountRepositoryAdapter implements LoadCustomerByAccountRepository {
    private final CustomersJpaRepository customersJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadCustomerResult execute(
          UUID accountId) {

        try {
            var entity = customersJpaRepository
                  .findByAccountId(accountId);

            if (entity.isPresent()) {
                return new CustomerLoaded(customer(entity.get()));
            } else {
                return new CustomerNotFound();
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CustomerLoadFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    Customer customer(CustomerEntity entity) {

        return new Customer(
              entity.getId(),
              entity.getCustomerId(),
              entity.getAccountId());

    }

}

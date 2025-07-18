// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.LoadCustomerRepository;
import tech.javafullstack.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoadFailed;
import tech.javafullstack.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoaded;
import tech.javafullstack.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerNotFound;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load customer repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class LoadCustomerRepositoryAdapter implements LoadCustomerRepository {
    private final CustomersJpaRepository customersJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadCustomerResult execute(
          String customerId) {

        try {
            var entity = customersJpaRepository.findByCustomerId(customerId);

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

    private Customer customer(CustomerEntity entity) {

        return new Customer(
              entity.getId(),
              entity.getCustomerId(),
              entity.getAccountId()
        );

    }

}

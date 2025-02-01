// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerNotFound;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class LoadCustomerRepositoryAdapter implements LoadCustomerRepository {
    private final CustomersJpaRepository customersJpaRepository;

    @Override
    public LoadCustomerResult execute(
          String customerId) {

        try {
            var entity = customersJpaRepository
                  .findByCustomerId(customerId);

            if (entity.isPresent()) {
                return new CustomerLoaded(
                      customer(entity.get()));
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

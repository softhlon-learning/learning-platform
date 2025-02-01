// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerNotFound;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class LoadCustomerByAccountRepositoryAdapter implements LoadCustomerByAccountRepository {
    private final CustomersJpaRepository customersJpaRepository;

    @Override
    public LoadCustomerResult execute(
          UUID accountId) {

        try {
            var entity = customersJpaRepository
                  .findByAccountId(accountId);

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

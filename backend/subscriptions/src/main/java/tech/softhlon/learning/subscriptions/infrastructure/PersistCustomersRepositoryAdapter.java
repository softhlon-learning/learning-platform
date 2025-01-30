// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerResult.CustomerPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerResult.CustomerPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class PersistCustomersRepositoryAdapter implements PersistCustomersRepository {
    private final CustomersJpaRepository customersJpaRepository;

    @Override
    public PersistCustomerResult execute(
          PersistCustomerRequest request) {

        try {
            customersJpaRepository.save(
                  entity(request));

            return new CustomerPersisted();

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new CustomerPersistenceFailed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CustomerEntity entity(
          PersistCustomerRequest request) {

        return CustomerEntity.builder()
              .id(request.id())
              .customerId(request.customerId())
              .accountId(request.accountId())
              .build();

    }

}

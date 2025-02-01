// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.CreateCustomerService.CreateCustomerResult.CustomerCreated;
import tech.softhlon.learning.subscriptions.domain.CreateCustomerService.CreateCustomerResult.CustomerCreationFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.Customer;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerNotFound;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerResult.CustomerPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerResult.CustomerPersistenceFailed;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class CreateCustomerServiceImpl implements CreateCustomerService {
    private final LoadCustomerRepository loadCustomerRepository;
    private final PersistCustomersRepository persistCustomersRepository;

    @Override
    public CreateCustomerResult execute(
          String customerId,
          UUID accountId) {

        try {

            var result = loadCustomerRepository.execute(customerId);

            return switch (result) {
                case CustomerLoaded(Customer customer) -> new CustomerCreated();
                case CustomerNotFound() -> persist(customerId, accountId);
                case CustomerLoadFailed(Throwable cause) -> new CustomerCreationFailed(cause);
            };

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new CustomerCreationFailed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    CreateCustomerResult persist(
          String customerId,
          UUID accountId) {

        var result = persistCustomersRepository.execute(
              null,
              customerId,
              accountId);

        return switch (result) {
            case CustomerPersisted() -> new CustomerCreated();
            case CustomerPersistenceFailed(Throwable cause) -> new CustomerCreationFailed(cause);
        };

    }

}

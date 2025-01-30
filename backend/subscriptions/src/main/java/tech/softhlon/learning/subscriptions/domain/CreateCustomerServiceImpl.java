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
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerRequest;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoadLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerNotFound;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerRequest;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerResult.CustomerPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerResult.CustomerPersistenceFailed;

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
          CreateCustomerRequest request) {

        try {

            var result = loadCustomerRepository.execute(
                  new LoadCustomerRequest(request.customerId()));

            return switch (result) {
                case CustomerLoadLoaded(Customer customer) -> new CustomerCreated();
                case CustomerNotFound() -> persist(request);
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
          CreateCustomerRequest request) {

        var result = persistCustomersRepository.execute(
              new PersistCustomerRequest(
                    null,
                    request.customerId(),
                    request.accountId()));

        return switch (result) {
            case CustomerPersisted() -> new CustomerCreated();
            case CustomerPersistenceFailed(Throwable cause) -> new CustomerCreationFailed(cause);
        };

    }

}

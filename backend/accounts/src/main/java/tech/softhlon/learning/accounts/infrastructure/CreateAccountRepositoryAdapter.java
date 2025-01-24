// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CreateAccountRepository;
import tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersisted;
import tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailed;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CreateAccountRepositoryAdapter implements CreateAccountRepository {

    private final AccountsJpaRepository accountsRepo;

    @Override
    public CreateAccountResult execute(
          CreateAccountRequest createAccountRequest) {

        try {

            var createdAccount = accountsRepo.save(
                  toAccount(createAccountRequest));

            return new AccountPersisted(
                  createdAccount.getId());

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new AccountPersistenceFailed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private AccountEntity toAccount(
          CreateAccountRequest request) {

        return AccountEntity.builder()
              .type(request.type())
              .name(request.name())
              .email(request.email())
              .password(request.password())
              .build();

    }

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadAccountRepositoryAdapter implements LoadAccountRepository {

    private final AccountsJpaRepository accountsRepo;

    @Override
    public LoadAccountResult execute(
          LoadAccountRequest request) {

        try {

            var accountEntity = accountsRepo.findById(
                  request.id());

            return accountEntity.isPresent()
                  ? new AccountLoaded(toAccount(accountEntity.get()))
                  : new AccountNotFound();

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new AccountLoadFailed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private LoadAccountRepository.Account toAccount(
          AccountEntity entity) {

        return new Account(
              entity.getId(),
              entity.getType(),
              entity.getName(),
              entity.getEmail(),
              entity.getPassword(),
              entity.isDeleted()
        );

    }

}

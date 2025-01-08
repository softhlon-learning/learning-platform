// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.infrastructure;

import io.softhlon.learning.accounts.domain.LoadAccountRepository;
import io.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import io.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import io.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public LoadAccountResult execute(LoadAccountRequest request) {
        try {
            var accountEntity = accountsRepo.findById(request.id());
            return accountEntity.isPresent()
                  ? new AccountLoaded(toAccount(accountEntity.get()))
                  : new AccountNotFound();
        } catch (Throwable cause) {
            log.error("", cause);
            return new AccountLoadFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private LoadAccountRepository.Account toAccount(AccountEntity entity) {
        return new Account(
              entity.getId(),
              entity.getName(),
              entity.getEmail()
        );
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.LoadAccountRepository;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load account repository adapter implementation..
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadAccountRepositoryAdapter implements LoadAccountRepository {

    private final AccountsJpaRepository accountsRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadAccountResult execute(
          UUID accountId) {

        try {
            var accountEntity = accountsRepo.findById(accountId);
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

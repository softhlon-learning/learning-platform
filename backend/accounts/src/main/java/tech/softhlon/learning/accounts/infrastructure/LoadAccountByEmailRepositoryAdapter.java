// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountIsDeleted;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.LoadAccountFailed;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load account by email repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadAccountByEmailRepositoryAdapter implements LoadAccountByEmailRepository {

    private final AccountsJpaRepository accountsRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadAccountByEmailResult execute(
          String email) {

        try {
            var entity = accountsRepo.findByEmail(email);
            return entity.isPresent()
                  ? existingAccount(entity.get())
                  : new AccountNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new LoadAccountFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private LoadAccountByEmailResult existingAccount(
          AccountEntity entity) {

        return entity.isDeleted()
              ? new AccountIsDeleted()
              : new AccountFound(toAccount(entity));

    }

    private Account toAccount(
          AccountEntity entity) {

        return new Account(
              entity.getId(),
              entity.getEmail(),
              entity.getPassword()
        );

    }

}

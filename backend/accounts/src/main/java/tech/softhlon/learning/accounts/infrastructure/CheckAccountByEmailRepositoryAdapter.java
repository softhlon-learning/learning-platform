// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;

import static tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckAccountByEmailRepositoryAdapter implements CheckAccountByEmailRepository {

    private final AccountsJpaRepository accountsRepo;

    @Override
    public CheckAccountByEmailResult execute(
          String email) {

        try {
            var entity = accountsRepo.findByEmail(
                  email);

            return entity.isPresent()
                  ? existingAccount(entity.get())
                  : new AccountNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckAccountFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CheckAccountByEmailResult existingAccount(
          AccountEntity entity) {

        return entity.isDeleted()
              ? new AccountIsDeleted()
              : new AccountExists(entity.getId());

    }

}

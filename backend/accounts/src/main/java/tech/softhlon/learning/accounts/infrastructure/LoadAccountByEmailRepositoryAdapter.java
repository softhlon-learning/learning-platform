// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.LoadAccountFailed;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadAccountByEmailRepositoryAdapter implements LoadAccountByEmailRepository {
    private final AccountsJpaRepository accountsRepo;

    @Override
    public LoadAccountByEmailResult execute(LoadAccountByEmailRequest request) {
        try {
            var entity = accountsRepo.findByEmail(request.email());
            return entity.isPresent()
                  ? new AccountFound(toAccunt(entity.get()))
                  : new AccountNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new LoadAccountFailed(cause);
        }
    }

    private Account toAccunt(AccountEntity entity) {
        return new Account(
              entity.getEmail(),
              entity.getPassword());
    }
}

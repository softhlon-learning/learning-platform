// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.LoadAccountRepository.Account;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountRequest;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountNotFoundInDatabase;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersisted;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersistenceFailed;
import tech.javafullstack.accounts.domain.UpdateProfileService.Result.AccountNotFoundFailed;
import tech.javafullstack.accounts.domain.UpdateProfileService.Result.Failed;
import tech.javafullstack.accounts.domain.UpdateProfileService.Result.Succeeded;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Updated profile service implemtation.
 */
@Service
@RequiredArgsConstructor
class UpdateProfileServiceImpl implements UpdateProfileService {

    private static final String ACCOUNT_NOT_FOUND = "Account not found";
    private final LoadAccountRepository loadAccountRepository;
    private final PersistAccountRepository persistAccountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          UUID accountId,
          String name) {

        var result = loadAccountRepository.execute(accountId);
        return switch (result) {
            case AccountLoaded(Account account) -> persistAccount(persistAccountRequest(account, name));
            case AccountNotFound() -> new AccountNotFoundFailed(ACCOUNT_NOT_FOUND);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistAccount(
          PersistAccountRequest persistAccountRequest) {

        var result = persistAccountRepository.execute(
              persistAccountRequest);

        return switch (result) {
            case AccountPersisted(UUID id) -> new Succeeded();
            case AccountNotFoundInDatabase() -> new AccountNotFoundFailed(ACCOUNT_NOT_FOUND);
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private PersistAccountRequest persistAccountRequest(
          Account account,
          String name) {

        return new PersistAccountRequest(
              account.id(),
              account.type(),
              name,
              account.email(),
              account.password(),
              account.isDeleted()
        );

    }

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.DeleteAccountService.Result.AccountIsAlreadyDeletedFailed;
import tech.softhlon.learning.accounts.domain.DeleteAccountService.Result.AccountNotFoundFailed;
import tech.softhlon.learning.accounts.domain.DeleteAccountService.Result.Failed;
import tech.softhlon.learning.accounts.domain.DeleteAccountService.Result.Succeeded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountRequest;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountNotFoundInDatabase;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersisted;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersistenceFailed;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class DeleteAccountServiceImpl implements DeleteAccountService {

    private static final String ACCOUNT_NOT_FOUND = "Account not found";
    private final LoadAccountRepository loadAccountRepository;
    private final PersistAccountRepository persistAccountRepository;

    @Override
    public Result execute(
          UUID accountId) {

        var result = loadAccountRepository.execute(accountId);
        return switch (result) {
            case AccountLoaded(Account account) -> deleteAccount(account);
            case AccountNotFound accountNotFound -> new AccountIsAlreadyDeletedFailed(ACCOUNT_NOT_FOUND);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result deleteAccount(Account account) {

        var result = persistAccountRepository.execute(
              new PersistAccountRequest(
                    account.id(),
                    account.type(),
                    account.name(),
                    account.email(),
                    account.password(),
                    true
              ));

        return switch (result) {
            case AccountPersisted(_) -> new Succeeded();
            case AccountNotFoundInDatabase() -> new AccountNotFoundFailed(ACCOUNT_NOT_FOUND);
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountRequest;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountNotFoundInDatabase;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersisted;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersistenceFailed;
import tech.softhlon.learning.accounts.domain.UpdateProfileService.Result.AccountNotFoundFailed;
import tech.softhlon.learning.accounts.domain.UpdateProfileService.Result.Failed;
import tech.softhlon.learning.accounts.domain.UpdateProfileService.Result.Succeeded;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class UpdateProfileServiceImpl implements UpdateProfileService {

    private static final String ACCOUNT_NOT_FOUND = "Account not found";
    private final LoadAccountRepository loadAccountRepository;
    private final PersistAccountRepository persistAccountRepository;

    @Override
    public Result execute(
          Request request) {

        var result = loadAccountRepository.execute(request.accountId());
        return switch (result) {
            case AccountLoaded(Account account) -> persistAccount(persistAccountRequest(account, request));
            case AccountNotFound() -> new AccountNotFoundFailed(ACCOUNT_NOT_FOUND);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistAccount(
          PersistAccountRequest persistAccountRequest) {

        var result = persistAccountRepository.execute(persistAccountRequest);

        return switch (result) {
            case AccountPersisted(UUID id) -> new Succeeded();
            case AccountNotFoundInDatabase() -> new AccountNotFoundFailed(ACCOUNT_NOT_FOUND);
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private PersistAccountRequest persistAccountRequest(
          Account account,
          Request request) {

        return new PersistAccountRequest(
              account.id(),
              account.type(),
              request.name(),
              account.email(),
              account.password(),
              account.isDeleted()
        );

    }

}

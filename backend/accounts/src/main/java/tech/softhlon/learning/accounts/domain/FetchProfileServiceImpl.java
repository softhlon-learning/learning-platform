// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.FetchProfileService.Result.Failed;
import tech.softhlon.learning.accounts.domain.FetchProfileService.Result.ProfileNotFoundFailed;
import tech.softhlon.learning.accounts.domain.FetchProfileService.Result.Succeeded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class FetchProfileServiceImpl implements FetchProfileService {

    private static final String PROFILE_NOT_FOUND = "Profile not fould";
    private final LoadAccountRepository loadAccountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          UUID accountId) {

        var result = loadAccountRepository.execute(accountId);
        return switch (result) {
            case AccountLoaded(Account account) -> new Succeeded(profile(account));
            case AccountNotFound() -> new ProfileNotFoundFailed(PROFILE_NOT_FOUND);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private ProfileView profile(
          Account account) {

        return new ProfileView(
              account.email(),
              account.name());

    }

}

// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.GetProfileService.Result.Failed;
import tech.softhlon.learning.accounts.domain.GetProfileService.Result.ProfileNotFoundFailed;
import tech.softhlon.learning.accounts.domain.GetProfileService.Result.Succeeded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountRequest;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class GetProfileServiceImpl implements GetProfileService {
    private static final String PROFILE_NOT_FOUND = "Profile not fould";
    private final LoadAccountRepository loadAccountRepository;

    @Override
    public Result execute(Request request) {
        var result = loadAccountRepository.execute(new LoadAccountRequest(request.accountId()));
        return switch (result) {
            case AccountLoaded(Account account) -> new Succeeded(profile(account));
            case AccountNotFound() -> new ProfileNotFoundFailed(PROFILE_NOT_FOUND);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Profile profile(Account account) {
        return new Profile(account.email(), account.name());
    }
}

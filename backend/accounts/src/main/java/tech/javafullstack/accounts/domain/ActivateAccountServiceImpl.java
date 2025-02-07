// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.Failed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.InvalidTokenFailed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.Succeeded;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenLoadFailed;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenLoaded;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenNotFound;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class ActivateAccountServiceImpl implements ActivateAccountService {

    private static final String INVALID_TOKEN = "Invalid account activation token";
    private static final String EXPIRED_TOKEN = "Account activation token has expired";

    private final LoadAccountTokenRepository loadAccountTokenRepository;

    @Override
    public Result execute(String token) {

        var result = loadAccountTokenRepository.execute(token);

        return switch (result) {
            case TokenLoaded tokenLoaded -> new Succeeded();
            case TokenNotFound() -> new InvalidTokenFailed("");
            case TokenLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

}

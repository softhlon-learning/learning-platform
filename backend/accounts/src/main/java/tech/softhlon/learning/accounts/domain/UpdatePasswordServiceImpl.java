// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenRequest;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoaded;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenNotFound;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.PasswordToken;
import tech.softhlon.learning.accounts.domain.UpdatePasswordService.Result.Failed;
import tech.softhlon.learning.accounts.domain.UpdatePasswordService.Result.InvalidTokenFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class UpdatePasswordServiceImpl implements UpdatePasswordService {
    private static final String INVALID_TOKEN = "Invalid reset password token";

    private final LoadPasswordTokenRepository loadPasswordTokenRepository;
    private final LoadAccountRepository loadAccountRepository;
    private final DeletePasswordTokenRepository deletePasswordTokenRepository;

    @Override
    public Result execute(
          Request request) {

        var loadTokenResult = loadPasswordTokenRepository.execute(
              new LoadPasswordTokenRequest(request.token()));

        return switch (loadTokenResult) {
            case TokenLoaded(PasswordToken token) -> processTokenUpdate(token);
            case TokenNotFound() -> new InvalidTokenFailed(INVALID_TOKEN);
            case TokenLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    Result processTokenUpdate(
          PasswordToken token) {

        return null;

    }

}

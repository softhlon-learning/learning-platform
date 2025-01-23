// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenRequest;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersisted;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersistenceFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailRequest;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountIsDeleted;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.LoadAccountFailed;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Result.EmailNotFoundFailed;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Result.Failed;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Result.Succeeded;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class RecoverPasswordServiceImpl implements RecoverPasswordService {
    private static final String EMAIL_NOT_FOUND = "Email not found";
    private final LoadAccountByEmailRepository loadAccountByEmailRepository;
    private final CreatePasswordTokenRepository createPasswordTokenRepository;

    @Override
    public Result execute(Request request) {
        var result = loadAccountByEmailRepository.execute(
              new LoadAccountByEmailRequest(request.email()));
        return switch (result) {
            case AccountFound(Account account) -> processPasswordRecovery(account);
            case AccountIsDeleted(), AccountNotFound() -> new EmailNotFoundFailed(EMAIL_NOT_FOUND);
            case LoadAccountFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result processPasswordRecovery(Account account) {
        var result = createPasswordTokenRepository.execute(
              passwordTokenRequest(account));
        return switch (result) {
            case PasswordTokenPersisted passwordTokenPersisted -> sendEmail(account);
            case PasswordTokenPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private CreatePasswordTokenRequest passwordTokenRequest(Account account) {
        return new CreatePasswordTokenRequest(
              account.id(),
              UUID.randomUUID().toString(),
              OffsetDateTime.now().plusDays(1));
    }

    private Result sendEmail(Account account) {
        return new Succeeded();
    }
}

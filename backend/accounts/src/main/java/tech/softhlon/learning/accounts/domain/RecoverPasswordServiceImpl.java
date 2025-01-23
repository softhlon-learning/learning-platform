// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailRequest;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountExists;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountIsDeleted;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.CheckAccountFailed;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Result.EmailNotFoundFailed;
import tech.softhlon.learning.accounts.domain.RecoverPasswordService.Result.Failed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class RecoverPasswordServiceImpl implements RecoverPasswordService {
    private static final String EMAIL_NOT_FOUND = "Email not found";
    private final CheckAccountByEmailRepository checkAccountByEmailRepository;

    @Override
    public Result execute(Request request) {
        var result = checkAccountByEmailRepository.execute(
              new CheckAccountByEmailRequest(request.email()));
        return switch (result) {
            case AccountExists accountExists -> null;
            case AccountIsDeleted(), AccountNotFound() -> new EmailNotFoundFailed(EMAIL_NOT_FOUND);
            case CheckAccountFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result processPasswordRecovery(Request request) {
        return null;
    }
}

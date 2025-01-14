// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailRequest;
import static tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.*;
import static tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountRequest;
import static tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersisted;
import static tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailed;
import static tech.softhlon.learning.accounts.domain.SignUpService.Result.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class SignUpServiceImpl implements tech.softhlon.learning.accounts.domain.SignUpService {
    private final tech.softhlon.learning.accounts.domain.CreateAccountRepository createAccountRepository;
    private final tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository checkAccountByEmailRepository;

    @Override
    public Result execute(Request request) {
        var exists = checkAccountByEmailRepository.execute(new CheckAccountByEmailRequest(request.email()));
        return switch (exists) {
            case AccountExists(_) -> new AccountAlreadyExistsFailed("Account with the same email already exists");
            case AccountNotFound() -> persistAccount(request);
            case CheckAccountFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistAccount(Request request) {
        var result = createAccountRepository.execute(prepareRequest(request));
        return switch (result) {
            case AccountPersisted(UUID id) -> new Succeeded(id);
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private CreateAccountRequest prepareRequest(Request request) {
        return new CreateAccountRequest(
              tech.softhlon.learning.accounts.domain.AccountType.PASSWORD.name(),
              request.name(),
              request.email(),
              encryptPassword(request.password()),
              tech.softhlon.learning.accounts.domain.AccountStatus.ACTIVE.name());
    }

    private String encryptPassword(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}

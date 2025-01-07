// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailRequest;
import static io.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.*;
import static io.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountRequest;
import static io.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailure;
import static io.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPesisted;
import static io.softhlon.learning.accounts.domain.SignUpService.Result.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class SignUpServiceImpl implements SignUpService {
    private final CreateAccountRepository createAccountRepository;
    private final CheckAccountByEmailRepository checkAccountByEmailRepository;

    @Override
    public Result signUp(Request request) {
        var exists = checkAccountByEmailRepository.execute(new CheckAccountByEmailRequest(request.email()));
        return switch (exists) {
            case AccountExists() -> new AccountAlreadyExists("Account with the same email already exists");
            case AccountNotFound() -> persistAccount(request);
            case CheckAccountFailure(Throwable cause) -> new InternalFailure(cause);
        };
    }

    private Result persistAccount(Request request) {
        var result = createAccountRepository.execute(prepareRequest(request));
        return switch (result) {
            case AccountPesisted(UUID id) -> new Success(id);
            case AccountPersistenceFailure(Throwable cause) -> new InternalFailure(cause);
        };
    }

    private CreateAccountRequest prepareRequest(Request request) {
        return new CreateAccountRequest(
              request.name(),
              request.email(),
              encryptPassword(request.password()),
              AccountStatus.ACTIVE.name());
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}

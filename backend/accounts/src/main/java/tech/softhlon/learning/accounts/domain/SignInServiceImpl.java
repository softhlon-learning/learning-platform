// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.Account;
import static tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailRequest;
import static tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.*;
import static tech.softhlon.learning.accounts.domain.SignInService.Result.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class SignInServiceImpl implements SignInService {
    private static final String AUTH_ERORR_MESSAGE = "Authentication failed. Incorrect email or password";
    private final LoadAccountByEmailRepository loadAccountByEmailRepository;
    private final JwtService jwtService;

    @Override
    public Result execute(Request request) {
        var exists = loadAccountByEmailRepository.execute(new LoadAccountByEmailRequest(request.email()));
        return switch (exists) {
            case AccountFound(Account account) -> authemticate(request, account);
            case AccountNotFound(), AccountIsDeleted() -> new InvalidCredentialsFailed(AUTH_ERORR_MESSAGE);
            case LoadAccountFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result authemticate(Request request, Account account) {
        var passwordEncoder = new BCryptPasswordEncoder();
        var matches = passwordEncoder.matches(request.password(), account.password());
        return matches
              ? new Succeeded(token(account))
              : new InvalidCredentialsFailed(AUTH_ERORR_MESSAGE);
    }

    private String token(Account account) {
        return jwtService.generateToken(
              account.id(),
              account.email());
    }
}

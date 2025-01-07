// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountRequest;
import static io.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailure;
import static io.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPesisted;
import static io.softhlon.learning.accounts.domain.SignUpService.Result.InternalFailure;
import static io.softhlon.learning.accounts.domain.SignUpService.Result.Success;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class SignUpServiceImpl implements SignUpService {
    private final CreateAccountRepository createAccountRepository;

    @Override
    public Result signUp(Request request) {
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
              request.password(),
              AccountStatus.ACTIVE.name());
    }
}

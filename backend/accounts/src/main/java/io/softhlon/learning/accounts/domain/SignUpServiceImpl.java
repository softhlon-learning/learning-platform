// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class SignUpServiceImpl implements SignUpService {
    private final CreateAccountRepository createAccountRepository;

    @Override
    public Result signUp(Request request) {
        var result = createAccountRepository.execute(prepareRequest(request));

        return switch (result) {
            case CreateAccountRepository.Result.Success(UUID id) -> new Result.Success(id);
            case CreateAccountRepository.Result.InternalFailure(Throwable cause) -> new Result.InternalFailure(cause);
        };
    }

    private CreateAccountRepository.Request prepareRequest(Request request) {
        return new CreateAccountRepository.Request(
              request.name(),
              request.email(),
              request.password(),
              AccountStatus.ACTIVE.name());
    }
}

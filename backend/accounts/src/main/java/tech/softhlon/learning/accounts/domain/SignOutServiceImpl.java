// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckTokenResult.CheckTokenFailed;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckTokenResult.TokenExists;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckTokenResult.TokenNotFound;
import tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenRequest;
import tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersisted;
import tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersistenceFailed;
import tech.softhlon.learning.accounts.domain.SignOutService.Result.Failed;
import tech.softhlon.learning.accounts.domain.SignOutService.Result.NotAuthorized;
import tech.softhlon.learning.accounts.domain.SignOutService.Result.Succeeded;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class SignOutServiceImpl implements SignOutService {

    private final CheckTokenRepository checkTokenRepository;
    private final CreateInvalidatedTokenRepository createInvalidatedTokenRepository;
    private final JwtService jwtService;

    @Override
    public Result execute(
          Request request) {

        try {

            if (request.token() == null) {
                return new NotAuthorized("Authentication token not found");
            }

            var exists = checkTokenRepository.execute(
                  tokenHash(request));

            return switch (exists) {
                case TokenExists() -> new Succeeded();
                case TokenNotFound() -> persistInvalidatedToken(request);
                case CheckTokenFailed(Throwable cause) -> new Failed(cause);
            };

        } catch (Throwable cause) {
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private String tokenHash(
          Request request) throws NoSuchAlgorithmException {

        return jwtService.tokenHash(
              request.token());

    }

    private Result persistInvalidatedToken(
          Request request) throws NoSuchAlgorithmException {

        var result = createInvalidatedTokenRepository.execute(
              new CreateInvalidatedTokenRequest(
                    jwtService.tokenHash(
                          request.token())));

        return switch (result) {
            case InvalidatedTokenPersisted(UUID id) -> new Succeeded();
            case InvalidatedTokenPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}

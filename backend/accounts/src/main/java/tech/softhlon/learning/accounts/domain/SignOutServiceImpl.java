// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CheckTokenRepository.CheckTokenResult.CheckTokenFailed;
import tech.javafullstack.accounts.domain.CheckTokenRepository.CheckTokenResult.TokenExists;
import tech.javafullstack.accounts.domain.CheckTokenRepository.CheckTokenResult.TokenNotFound;
import tech.javafullstack.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersisted;
import tech.javafullstack.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersistenceFailed;
import tech.javafullstack.accounts.domain.SignOutService.Result.Failed;
import tech.javafullstack.accounts.domain.SignOutService.Result.NotAuthorized;
import tech.javafullstack.accounts.domain.SignOutService.Result.Succeeded;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Sign out service implementation.
 */
@Service
@RequiredArgsConstructor
class SignOutServiceImpl implements SignOutService {

    private final CheckTokenRepository checkTokenRepository;
    private final CreateInvalidatedTokenRepository createInvalidatedTokenRepository;
    private final JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          String token) {

        try {
            if (token == null) {
                return new NotAuthorized("Authentication token not found");
            }

            var exists = checkTokenRepository.execute(
                  tokenHash(token)
            );

            return switch (exists) {
                case TokenExists() -> new Succeeded();
                case TokenNotFound() -> persistInvalidatedToken(token);
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
          String token) throws NoSuchAlgorithmException {

        return jwtService.tokenHash(
              token);

    }

    private Result persistInvalidatedToken(
          String token) throws NoSuchAlgorithmException {

        var result = createInvalidatedTokenRepository.execute(
              jwtService.tokenHash(token)
        );

        return switch (result) {
            case InvalidatedTokenPersisted(UUID id) -> new Succeeded();
            case InvalidatedTokenPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}

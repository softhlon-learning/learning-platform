// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckAuthTokenRequest;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckAuthTokenResult.*;
import static tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenRequest;
import static tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersisted;
import static tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersistenceFailed;
import static tech.softhlon.learning.accounts.domain.SignOutService.Result.Failed;
import static tech.softhlon.learning.accounts.domain.SignOutService.Result.Succeeded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class SignOutServiceImpl implements SignOutService {
    private final CheckTokenRepository checkTokenRepository;
    private final CreateInvalidatedTokenRepository createInvalidatedTokenRepository;

    @Override
    public Result signOut(Request request) {
        try {
            var exists = checkTokenRepository.execute(prepareRequest(request));
            return switch (exists) {
                case AuthTokenExists() -> new Succeeded();
                case AuthTokenNotFound() -> persistInvalidatedToken(request);
                case CheckAuthTokenFailed(Throwable cause) -> new Failed(cause);
            };
        } catch (Throwable cause) {
            return new Failed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CheckAuthTokenRequest prepareRequest(Request request) throws NoSuchAlgorithmException {
        return new CheckAuthTokenRequest(tokenHash(request.token()));
    }

    private String tokenHash(String token) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(token.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    private Result persistInvalidatedToken(Request request) throws NoSuchAlgorithmException {
        var result = createInvalidatedTokenRepository.execute(
              new CreateInvalidatedTokenRequest(
                    tokenHash(request.token())));
        return switch (result) {
            case InvalidatedTokenPersisted(UUID id) -> new Succeeded();
            case InvalidatedTokenPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }
}
